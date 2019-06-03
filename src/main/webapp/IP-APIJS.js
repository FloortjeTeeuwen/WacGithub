
function initPage() {
    fetch('https://ipapi.co/83.82.232.215/json/')
        .then(function(response) {
            return response.json();

        })
        .then(function(myJson) {
            const country = document.querySelector("#country");
            country.textContent = myJson.country;

            const country_name = document.querySelector("#country_name");
            country_name.textContent = myJson.country_name;

            const region = document.querySelector("#region");
            region.textContent = myJson.region;

            const city = document.querySelector("#city");
            city.textContent = myJson.city;

            const postal = document.querySelector("#postal");
            postal.textContent = myJson.postal;

            const latitude = document.querySelector("#latitude");
            latitude.textContent = myJson.latitude;

            const longitude = document.querySelector("#longitude");
            longitude.textContent = myJson.longitude;

            const ip = document.querySelector("#ip");
            ip.textContent = myJson.ip;


            showWeather(myJson.latitude,myJson.longitude, myJson.city);

        });
    loadCountries();
}


function saveToLS(myJson, city) {
    const weatherdata = myJson;
    weatherdata.timestamp = Date.now();
    window.localStorage.setItem(city, JSON.stringify(weatherdata));
}

function showWeather(latitude, longitude, city) {
    var localCity = window.localStorage.getItem(city);
    if(localCity === null || is10MinutesAgo(localCity.timestamp)) {
        getWeatherData(latitude, longitude)
            .then(myJson => {
                fillWeatherDiv(myJson, city);
                saveToLS(myJson, city);
            });
    } else {
        var myJson = JSON.parse(window.localStorage.getItem(city));
        fillWeatherDiv(myJson,city);
    }
}

function getWeatherData(latitude,longitude){
    return fetch('https://api.openweathermap.org/data/2.5/weather?lat='+latitude+'&lon='+longitude+'&appid=e42ce15cf9205e45b613fa448eb477ec')
        .then(function (response) {
            return response.json();
        });
}


function fillWeatherDiv(myJson, city){

    const temp = document.querySelector("#temp");
    temp.textContent = myJson.main.temp;

    const humidity = document.querySelector("#humidity");
    humidity.textContent = myJson.main.humidity;

    const speed = document.querySelector("#speed");
    speed.textContent = myJson.wind.speed;

    const gust = document.querySelector("#gust");
    gust.textContent = myJson.wind.gust;

    const sunrise = document.querySelector("#sunrise");
    sunrise.textContent = myJson.sys.sunrise;

    const sunset = document.querySelector("#sunset");
    sunset.textContent = myJson.sys.sunset;

    var cityname=document.getElementById("Weather");
    cityname.textContent = (`Het weer in ${city}`);
}

function is10MinutesAgo(date2) {
    var date1 = Date.now();
    var res = Math.abs(date1 - date2) / 1000;
    var minutes = Math.floor(res / 60) % 60;

    return minutes > 10;
}

function loadCountries() {
    fetch("http://localhost:8181/restservices/countries")
        .then(function (response) {
            return response.json();
        })
        .then(function (myJson) {
            countries.innerHTML = '';
            for(const country of myJson) {
                const row = document.createElement("tr");

                row.setAttribute('onclick',"showWeather("+country.lat+", "+country.lng+", '"+country.capital+"')");
                row.setAttribute("id", country.code);

                const cell1 = document.createElement("td");
                const cell2 = document.createElement("td");
                const cell3 = document.createElement("td");
                const cell4 = document.createElement("td");
                const cell5 = document.createElement("td");
                const cell6 = document.createElement("td");

                var deleteButton = document.createElement("button");
                deleteButton.innerHTML = "Verwijder";

                deleteButton.addEventListener("click", (event) => {
                    // Event niet doorgeven aan andere listeners
                    event.stopPropagation();
                    deleteCountry(country);
                });

                var editButton = document.createElement("button");
                editButton.innerHTML = "Bewerk";

                editButton.addEventListener("click", () => {
                    // Event niet doorgeven aan andere listeners
                    event.stopPropagation();
                    editCountry(country);
                });



                cell1.appendChild(document.createTextNode(country.name));
                cell2.appendChild(document.createTextNode(country.capital));
                cell3.appendChild(document.createTextNode(country.region));
                cell4.appendChild(document.createTextNode(country.surface));
                cell5.appendChild(document.createTextNode(country.population));
                cell6.appendChild(deleteButton);
                cell6.appendChild(editButton);

                row.appendChild(cell1);
                row.appendChild(cell2);
                row.appendChild(cell3);
                row.appendChild(cell4);
                row.appendChild(cell5);
                row.appendChild(cell6);

                countries.appendChild(row);
            }});

}

function deleteCountry(country) {
    console.log("DELETE", country);
    fetch("http://localhost:8181/restservices/countries/"+country.code, { method: 'DELETE' })
        .then(function (response) {
            if (response.ok) {
                alert("Land verwijdert!");
                document.querySelector("#" + country.code).remove();
            } else {
                console.log("Cannot delete country");
            }
        }).catch(function (error) {
            console.log("Could not fetch: ", error);
        });

}

function editCountry(country) {
    console.log("EDIT", country);
    modal.style.display = "block";

    document.getElementById("Value1").value = country.code;
    document.getElementById("Value2").value = country.name;
    document.getElementById("Value3").value = country.continent;
    document.getElementById("Value4").value = country.region;
    document.getElementById("Value5").value = country.surface;
    document.getElementById("Value6").value = country.population;
    document.getElementById("Value7").value = country.government;


    document.getElementById("submitButton").addEventListener("click", function (event) {
        event.preventDefault();
        modal.style.display = "none";

        var formdata = new FormData (document.querySelector("#editForm"));
        var encData = new URLSearchParams(formdata.entries());



        fetch("http://localhost:8181/restservices/countries/"+country.code, { method: 'PUT', body: encData })
            .then(function (response) {
                if (response.ok) {
                    alert("Land geupdate!");
                    document.querySelector("#" + country.code);
                    loadCountries();
                } else {
                    console.log("Cannot update country");
                }
            }).catch(function (error) {
            console.log("Could not fetch: ", error);
        });
    });

}

function createCountry(country) {
    console.log("Create", country);
    modal2.style.display = "block";

    document.getElementById("AddButton2").addEventListener("click", function (event) {
        event.preventDefault();
        modal.style.display = "none";

        var formdata = new FormData (document.querySelector("#addForm"));
        var encData = new URLSearchParams(formdata.entries());

        fetch("http://localhost:8181/restservices/countries/newcountry", { method: 'POST', body: encData })
            .then(function (response) {
                console.log(response);
                if (response.ok) {
                    alert("Land opgeslagen!");
                    loadCountries();
                    document.querySelector("#" + country.code);
                } else {
                    console.log("Cannot save country");
                }
            }).catch(function (error) {
            console.log("Could not fetch: ", error);
        });
    });

}

// Get the modal
    var modal = document.getElementById("editModal");
    var modal2 = document.getElementById("createModal");



// Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];
    var span2 = document.getElementsByClassName("close1")[0];

var createButton = document.getElementById("AddButton");
createButton.addEventListener("click", () =>{
    createCountry(country);
});
// When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        modal.style.display = "none";
    };

    span2.onclick = function(){
        modal2.style.display = "none";
    };



// When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    };

    window.onclick = function(event){
        if (event.target === modal2){
            modal2.style.display = "none";
        }
    };

initPage();

    

