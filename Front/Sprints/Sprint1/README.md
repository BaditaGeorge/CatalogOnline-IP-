### Backlog

1. [HIGH] Redactarea unui Product Backlog
2. [HIGH] DoD pentru fiecare Sprint
3. [HIGH] Diagrama UML a view-urilor si a legaturilor dintre acestea
4. [MEDIUM] Diagrama C4 care sa transmita modul de comunicare inter-module
5. [GTH] Site-map care sa reflecte link-urile dintre paginile aplicatiei
6. [HIGH] Wireframe pe baza diagramei UML
7. [GTH] Design system
8. [MEDIUM] Identificarea tehnologiilor si resurselor necesare
9. [MEDIUM] Documentare tehnologii necesare
9'. [MEDIUM] Implementare clase de baza ale aplicatie
10. [HIGH] Implementare Router, clasa care gestioneaza view-urile aplicatie
11. [HIGHT] Implementare Main View, clasa de baza a plicatiei, start-ul flowurilor
12. [HIGH] Implementare formular inregistrare/login
13. [MEDIUM] Implementare clase repetitive:
	- Header (cuprinde controalele aferente userului, navigarea in aplicatie)
	- Footer (tipic)
14. [MEDIUM] Implementare view Setari Cont (specific tipului de utilizator)
15. Implementare view Student:
	- [HIGH] Navigare ani de studiu/semestre
	- [HIGH] Navigare discipline/optionale
	- [HIGH] Tabela note obtinute
	- [MEDIUM] Criterii de promovare
	- [GTH] Informatii despre curs
	- [GTH] Informatii personale
	- [GTH] Informatii profesori
	- [GTH] Semnaleaza greseala
	- [GTH] Orar
16. Implementare view Profesor
	- [HIGH] Navigare discipline predate
	- [HIGH] Navigare grupe
	- [HIGH] Tabel situatie (per disciplina, per grupa)
	- [HIGH] Tabel criterii promovare (editare, adaugare criterii)
	- [HIGH] Formule complexe de calcul al notelor paritiale/finale
	- [MEDIUM] Import/Export note
	- [GTH] Grafic progres semestrial
17. Implementare view Secretar
	- [HIGHT] Navigare ani de studiu/semian
	- [HIGH] Navigare grupe
	- [HIGH] Adaugare grupa
	- [HIGH] Adaugare situatie grupa (incarcare .csv/.pdf)
	- [GTH] Tabel date grupa
18. Implementare view Admin
	- [MEDIUM] Navigare categorii aplicatie (studenti, profesori, secretari, cursuri)
	- [GTH] Setari specifice facultatii (ani de studiu, impartirea in semi-ani, grupe, etc)
	- [MEDIUM] Tabel CRUD studenti 
	- [MEDIUM] Tabel CRUD profesori
	- [MEDIUM] Tabel CRUD cursuri
	- [GTH] Tabel CRUD administratori
19. [GTH] Responsive (mai putin Admin)
	
###DoD


[C ][DDL   ][Members          ]#Task

[2h][25 Mar[All]              #Module meeting (stabilire responsabilitati, directii 	generale)

[2h][25 Mar[Gabriel]          #Deffinition of Done doc, listare taskuri mandatory in primul sprint (responsabili + cost/ddl)

[2h][25 Mar[Gabriel]          #Un backlog (lista de taksuri generale ce trebuie indeplinite pe baza unor prioritatie pana la sfarsitul proiectului)

[1h][2 Apr][Gabriel]           #Documentare tehnologii

[1h][2 Apr][Gabriel]           #Implementare clase fara metode si instalare proiect

[6h][25 Mar[Emilian + Teona]  #Un site-map generalizat, posibile directii ale interfetei, stabilire succinta a unor metode/viewuri (Short guide pentru diagrame)

[6h][2 Apr ][Paul + Claudiu]   #Diagrama UML (Views Classes) - Modul de functionare al aplicatie la nivel de interfata, legaturile dintre view-uri si date

[6h][2 Apr ][George + Lician]  #O diagrama C4 in care sa putem observa felul in care interactioneaza modulul nostru cu celelalte module
