const loginButton = document.getElementById("loginButton");
loginButton.addEventListener("click", () =>{
    login()
});

function login() {
    var newLocation = "/IP-API.html";
    var formData = new FormData(
        document.querySelector("#loginForm"));
    var encData = new URLSearchParams(formData.entries());

    fetch("/restservices/authentication", { method: 'POST', body: encData })
        .then(function(response) {
            if (response.ok)
                return window.location.href = "/IP-API.html";
            else alert("Wrong username/password");
        })

        .then(myJson => window.sessionStorage.setItem("myJWT", myJson.JWT))

        .catch(error => console.log(error));}
