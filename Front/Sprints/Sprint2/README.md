## Nevoi

### 1. Metoda de la care primesc lista de discipline definite de profesor
`ex: {teacher: "Ionescu Ion", disciplines: [{id: 1, name: "Advance Math"]}`
### 2. Metoda la care pot crea o disciplina noua
`ex: {name: "OOP"}`
### 3. Metoda la care primesc lista de variabile din tabel (practic numele coloanelor)
`ex: {variables: ["A","B","C","D"]}`
### 4. Metoda la care pot trimite lista de variabile (practic adaugarea de variabile/actualizare)
`ex: {variables: ["A","B","C","D"]}`
### 5. Metoda la care primesc datele din tabel
`ex: {0: {student: "Popescu Costel", matricol: 23145641, values:[2, "*"]}}`
### 5. Metoda la care pot trimite datele actualizate din tabel pentru calcul
`ex: {0: {student: "Popescu Costel", matricol: 23145641, values:[2, "*"]}}`
### 6. Metoda la care primesc lista de formule/criterii de promovare
`ex: [{desc: 'Criteriul1',formule:'Ax*bas+asda',conditii:'>5',}]`
Aici sa se poata adauga in formula curenta alte rezultate calculate anterior de formula sau date din table (coloane)

### DoD 2
[C ][DDL   ][Members          ]#Task

[2h][23 Apr][All]              #Module meeting (stabilire responsabilitati, directii generale)
[2h][23 Apr][All]              #Documentare Rreac (Meeting de introducere in react)
[2h][23 Apr][Gabriel]          #Deffinition of Done doc, listare taskuri mandatory in al doilea sprint (responsabili + cost/ddl)
[2h][23 Apr][Gabriel]          #Tabel general cu datele stundetilor si coloane
[2h][23 Apr][Gabriel]          #Actiuni default in table (Adaugare coloane, Adaugare rows, stergere/actualizare coloane/rows)
[2h][23 Apr][George]           #Filtre complex in tabelul Catalog si sortare
[2h][23 Apr][Lucian]           #Input adaugare formula
[2h][23 Apr][Claudiu]          #Dashboard profesor si integrare cu celelalte componente
[1h][23Apr][Paul]              #Header cu selector pentru discipline
[1h][23Apr][Gabriel]           #Implementare clase fara metode si instalare proiect
[6h][23 Apr][Emilian + Teona]  #Wireframe-uri si UI aplicatie + CSS
