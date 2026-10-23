"""Hops Grill backend: a small JSON HTTP API using only the standard library.

Run:  python backend.py [port]      (default port 8000)

Endpoints:
    GET  /health                 -> {"status": "ok"}
    GET  /menu[?category=GRILL]  -> list of menu items
    POST /orders                 -> price an order
         body: {"items": [{"name": "Ribeye Steak", "quantity": 2}, ...]}
"""
import json
import sys
from decimal import ROUND_HALF_UP, Decimal
from http.server import BaseHTTPRequestHandler, HTTPServer
from urllib.parse import parse_qs, urlparse

TAX_RATE = Decimal("0.08")
CENT = Decimal("0.01")

# Mirrors Menu.defaultMenu() in the Java code.
MENU = [
    {"name": "Onion Rings", "category": "STARTER", "price": Decimal("6.50")},
    {"name": "Ribeye Steak", "category": "GRILL", "price": Decimal("28.00")},
    {"name": "BBQ Chicken", "category": "GRILL", "price": Decimal("18.50")},
    {"name": "IPA Pint", "category": "DRINK", "price": Decimal("7.00")},
    {"name": "Brownie", "category": "DESSERT", "price": Decimal("8.00")},
]
MENU_BY_NAME = {item["name"].lower(): item for item in MENU}


def money(value):
    return str(value.quantize(CENT, rounding=ROUND_HALF_UP))


def menu_json(items):
    return [{**i, "price": money(i["price"])} for i in items]


def price_order(payload):
    """Return (result, error). Quantities merge per item, like Order.add in Java."""
    lines = {}
    entries = payload.get("items") if isinstance(payload, dict) else None
    if not isinstance(entries, list) or not entries:
        return None, "items must be a non-empty list"
    for entry in entries:
        if not isinstance(entry, dict):
            return None, "each item must be an object"
        name, qty = entry.get("name"), entry.get("quantity", 1)
        if not isinstance(name, str) or name.lower() not in MENU_BY_NAME:
            return None, "unknown item: {}".format(name)
        if not isinstance(qty, int) or isinstance(qty, bool) or qty <= 0:
            return None, "quantity must be a positive integer"
        key = name.lower()
        lines[key] = lines.get(key, 0) + qty

    out_lines, subtotal = [], Decimal("0")
    for key, qty in lines.items():
        item = MENU_BY_NAME[key]
        line_total = item["price"] * qty
        subtotal += line_total
        out_lines.append({"name": item["name"], "quantity": qty, "lineTotal": money(line_total)})
    subtotal = subtotal.quantize(CENT, rounding=ROUND_HALF_UP)
    tax = (subtotal * TAX_RATE).quantize(CENT, rounding=ROUND_HALF_UP)
    return {
        "lines": out_lines,
        "subtotal": money(subtotal),
        "tax": money(tax),
        "total": money(subtotal + tax),
    }, None


class Handler(BaseHTTPRequestHandler):
    def _send(self, status, body):
        data = json.dumps(body).encode("utf-8")
        self.send_response(status)
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", str(len(data)))
        self.send_header("Access-Control-Allow-Origin", "*")
        self.end_headers()
        self.wfile.write(data)

    def do_GET(self):
        url = urlparse(self.path)
        if url.path == "/health":
            self._send(200, {"status": "ok"})
        elif url.path == "/menu":
            category = parse_qs(url.query).get("category", [None])[0]
            items = [i for i in MENU if not category or i["category"] == category.upper()]
            self._send(200, menu_json(items))
        else:
            self._send(404, {"error": "not found"})

    def do_POST(self):
        if urlparse(self.path).path != "/orders":
            self._send(404, {"error": "not found"})
            return
        try:
            length = int(self.headers.get("Content-Length", 0))
            payload = json.loads(self.rfile.read(length) or b"null")
        except (ValueError, UnicodeDecodeError):
            self._send(400, {"error": "invalid JSON"})
            return
        result, error = price_order(payload)
        if error:
            self._send(400, {"error": error})
        else:
            self._send(200, result)

    def do_OPTIONS(self):
        self.send_response(204)
        self.send_header("Access-Control-Allow-Origin", "*")
        self.send_header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
        self.send_header("Access-Control-Allow-Headers", "Content-Type")
        self.end_headers()


def main():
    port = int(sys.argv[1]) if len(sys.argv) > 1 else 8000
    server = HTTPServer(("127.0.0.1", port), Handler)
    print("Hops Grill backend listening on http://127.0.0.1:{}".format(port))
    try:
        server.serve_forever()
    except KeyboardInterrupt:
        pass
    finally:
        server.server_close()


if __name__ == "__main__":
    main()
