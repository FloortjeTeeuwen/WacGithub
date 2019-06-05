const loginButton = document.getElementById("loginButton");
loginButton.addEventListener("click", () =>{
    login()
});

function login() {
    var newLocation = "http://localhost:8181/IP-API.html";
    var formData = new FormData(
        document.querySelector("#loginForm"));
    var encData = new URLSearchParams(formData.entries());

    fetch("http://localhost:8181/restservices/authentication", { method: 'POST', body: encData })
        .then(function(response) {
            if (response.ok)
                return window.location.href = "http://localhost:8181/IP-API.html";
            else alert("Wrong username/password");
        })

        .then(myJson => window.sessionStorage.setItem("myJWT", myJson.JWT))

        .catch(error => console.log(error));}
