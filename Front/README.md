##Documentatie

##Tehnologii identificate:
###1. React JS (librarie javascript)
###2. NPM (via Node, package management)
###3. SASS/LESS (preporcesare css)
###4. Bootstrap React

##1. React Js
React este o librarie js care faciliteaza design-ul unor view-uri simple pentru fiecare stare a aplicatiei (single-page app). React se ocupa de randarea eficienta a fiecare componente doar in momentul in care aceasta se actualizeaza.
React are la baza Component care isi poate administra propriile stari si metode, iar acestea pot fi refolosite pe tot parcursul implementarii solutiei finale.

Aplicatia noastra isi propune sa utilizeze React pentru a modela o relatie intre o serie de view-uri esentiale (Student dashboard, Teacher dashboard, Secretary dashboard si Admin dashboard).
Toate aceste vor ingloba alte componente precum tabele, formulare, butoane etc.

Aplicatia are la baza componenta App a carei randare este declansata de rularea aplicatiei, iar aceasta are la baza un Router care faciliteaza generarea componentelor in functie de URL si tipul de user logat.

`class App extends Component {
  render() {
    return (
      <Router user={user}/>
    );
  }
}`

In Router se identifica urmatorea componenta generata pe baza userului logat
`<Router>
        <Suspense fallback={<div>Loading...</div>}>
            <Switch>
                <Route exact path="/login" component={Login}/>
                <Route path="/register" component={Register}/>
                <Route path="/student" component={StudentDashboard}/>
                <Route path="/teacher" component={ProfesorDashboard}/>
                <Route path="/secretary" component={SecretaryDashboard}/>
                <Route path="/admin" component={AdminDashboard}/>
                <Route path="/about" component={About}/>
            </Switch>
        </Suspense>
</Router>`

Fiecare componenta are propriile stari si metode, conforme diagramei de clase

Link-uri utile:
Instalation: `https://reactjs.org/docs/create-a-new-react-app.html`
Get Started (Cum sa incepi o aplicatie in React si explicarea conceptelor de baza): `https://reactjs.org/docs/getting-started.html`
Diferenta dintre .js si .jsx (faciliteaza scrierea html in format nativ): `https://reactjs.org/docs/introducing-jsx.html`
Componets (Celula de baza a React-ului/Aplicatie): `https://reactjs.org/docs/components-and-props.html`
Ciclul de viata a unei componente si starile prin care trece acesta (triggere de re-render etc.): `https://reactjs.org/docs/components-and-props.html`

###2. NPM
NPM (Node Packages Manager) este folosit pentru a instala si administra diferite pachete care intra in componenta librariei React, dar si a pachetelor externe precum React Bootsra, React Form, etc.
NPM se instaleaza via Node (`https://nodejs.org`), executabil usor de instalat.
Orice proiect dispunde de un package.json in care se gasesc informatii despre proiect, scripturi ce vor fi rulate de catre npm, dar si pachetele instalate de catre dev/user

###3. SASS/LESS
Incercam sa separam pe cat posibil stilizarea de logica componentelor si vom folosi SASS/LESS, preprocesor de .css care faciliteaza scrierea stilurilor
Ghid: (https://sass-lang.com/guide)

###4. Bootstrap React este cunoscut pentru componentele predefinite si usor de utilizat la nivel de aplicatie.
Acesta cuprinde componente simple precum butoane, modale, formulare, inputuri, selecturi, etc. stilizate si cu logica deja implementata pentru a face dezvoltarea unei aplicatii cat mai putin time consuming

Instalare: `npm install -s react-bootstrap`
Componente: (https://react-bootstrap.github.io/components/alerts)































