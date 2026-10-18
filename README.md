# Library Management System

A command-line Java application for maintaining a small book catalog. It supports adding, listing, searching, and deleting book records through an interactive menu.

## Features

- Register a book with its title, author, optional description, inventory quantity, and price.
- List all registered books with their details.
- Search for books by a partial match in the title, author, or description. Searches are case-insensitive.
- Delete a book by selecting its number from the displayed catalog.
- Validate required text fields, integer quantities, menu choices, and non-negative prices.
- Accept either a comma or a period as the decimal separator for prices.

## Requirements

- Java Development Kit (JDK) 8 or later.
- No third-party libraries are required.

## Project Structure

```text
src/
└── Gerencia/
    ├── Biblioteca.java
    └── Livros.java
```

`Biblioteca` contains the application menu and catalog operations. `Livros` defines the data fields for each book.

## Build and Run

From the project root in Windows PowerShell, compile the source files and start the application:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -encoding UTF-8 -d out src\Gerencia\Livros.java src\Gerencia\Biblioteca.java
java "-Dfile.encoding=UTF-8" -cp out Gerencia.Biblioteca
```

## Menu Options

| Option | Action |
| --- | --- |
| `1` | Add a book |
| `2` | Delete a book |
| `3` | List all books |
| `4` | Search the catalog |
| `0` | Exit the application |

## Data Storage

Book records are kept in memory while the application is running. They are cleared when the program exits; the current version does not save data to a file or database.
