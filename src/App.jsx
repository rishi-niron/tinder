import './App.css'

function App() {
  return (
    <>
      <header className="nav">
        <div className="nav-brand">Tesla Gitlab</div>
        <nav>
          <a href="#about">About</a>
          <a href="#features">Features</a>
          <a href="#contact">Contact</a>
        </nav>
      </header>

      <section className="hero">
        <h1>Build something great</h1>
        <p>A simple landing page starter, ready for you to customize.</p>
        <a className="cta" href="#contact">
          Get in touch
        </a>
      </section>

      <section id="about" className="section">
        <h2>About</h2>
        <p>
          This is a placeholder about section. Describe your project, product,
          or team here.
        </p>
      </section>

      <section id="features" className="section">
        <h2>Features</h2>
        <div className="features-grid">
          <div className="feature-card">
            <h3>Fast</h3>
            <p>Built with Vite for a fast development experience.</p>
          </div>
          <div className="feature-card">
            <h3>Simple</h3>
            <p>Minimal, easy-to-understand structure to build on.</p>
          </div>
          <div className="feature-card">
            <h3>Flexible</h3>
            <p>Extend it with the tools and libraries you need.</p>
          </div>
        </div>
      </section>

      <section id="contact" className="section">
        <h2>Contact</h2>
        <p>Reach out at hello@example.com</p>
      </section>

      <footer className="footer">
        <p>&copy; {new Date().getFullYear()} Tesla Gitlab. All rights reserved.</p>
      </footer>
    </>
  )
}

export default App
