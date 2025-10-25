# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [Unreleased]

### Added
- Comprehensive documentation structure
- Security validation guide covering RFC, NIST, and OWASP standards
- Contributing guidelines
- Code examples for validation

### Changed
- Reorganized documentation into `/docs` folder structure

---

## [1.0.0] - 2025-10-25

### Added
- Production-ready email validation (RFC 5321 & RFC 5322 compliant)
- Production-ready password validation (NIST SP 800-63B & OWASP compliant)
- Name validation with international character support
- Common password pattern detection using regex
- Weak pattern detection (sequential, repeated characters)
- Comprehensive error messages for better UX
- Form validation system with TanStack Form
- User authentication features (login & register)
- PandaCSS styling system
- Feature-based architecture
- Shared UI component library
- Type-safe routing with TanStack Router

### Security
- Email validation following RFC standards
  - Length constraints (max 254 chars)
  - Local part validation (max 64 chars)
  - Domain and TLD validation
  - Pattern validation (no consecutive dots, etc)
  
- Password security following NIST & OWASP
  - Min 8 chars, Max 128 chars
  - Character diversity requirements
  - Common password blocking
  - Weak pattern detection
  - No consecutive identical characters

- Name validation
  - Length validation (2-100 chars)
  - Character whitelist
  - Edge case handling

### Documentation
- Main README with project overview
- Architecture documentation
- Tech stack documentation
- Routing guide
- Best practices guide

---

## [0.1.0] - 2025-10-01

### Added
- Initial project setup
- TanStack Start configuration
- PandaCSS setup
- Basic routing structure
- Authentication pages (login)

---

## Version Number Guidelines

Given a version number MAJOR.MINOR.PATCH:

- **MAJOR**: Breaking changes, incompatible API changes
- **MINOR**: New features, backwards-compatible
- **PATCH**: Bug fixes, backwards-compatible

### Change Categories

- **Added**: New features
- **Changed**: Changes to existing functionality
- **Deprecated**: Soon-to-be removed features
- **Removed**: Removed features
- **Fixed**: Bug fixes
- **Security**: Security improvements or vulnerability fixes
- **Performance**: Performance improvements

---

## How to Update This File

When making changes:

1. Add entry under `[Unreleased]` section
2. Categorize change appropriately
3. Provide clear, descriptive entry
4. Link to PR or issue if applicable

Example:
```markdown
### Added
- Email validation with RFC 5322 compliance (#123)
- Password strength meter component (#456)

### Fixed
- Login form validation error on submit (#789)
```

When releasing:

1. Move `[Unreleased]` changes to new version section
2. Add release date
3. Create new empty `[Unreleased]` section
4. Update version links at bottom

---

## Release Schedule

- **Major releases**: As needed for breaking changes
- **Minor releases**: Monthly or when significant features are ready
- **Patch releases**: As needed for critical bug fixes

---

**Note**: Versions prior to 1.0.0 are considered beta/development versions and may have frequent breaking changes.

[Unreleased]: https://github.com/USERNAME/start/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/USERNAME/start/compare/v0.1.0...v1.0.0
[0.1.0]: https://github.com/USERNAME/start/releases/tag/v0.1.0

