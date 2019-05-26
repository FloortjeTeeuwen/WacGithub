
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
            loadCountries();

        });
}


function saveToLS(myJson, city) {
    console.log("saving to LS");
    console.log(myJson);
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
    console.log(myJson);

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

            for(const country of myJson) {
                const table = document.createElement("tr")
                table.setAttribute('onclick',"showWeather("+country.lat+", "+country.lng+", '"+country.capital+"')")
                const table1 = document.createElement("td");
                const table2 = document.createElement("td");
                const table3 = document.createElement("td");
                const table4 = document.createElement("td");
                const table5 = document.createElement("td");

                table1.appendChild(document.createTextNode(country.name));
                table2.appendChild(document.createTextNode(country.capital));
                table3.appendChild(document.createTextNode(country.region));
                table4.appendChild(document.createTextNode(country.surface));
                table5.appendChild(document.createTextNode(country.population));

                table.appendChild(table1);
                table.appendChild(table2);
                table.appendChild(table3);
                table.appendChild(table4);
                table.appendChild(table5);

                countries.appendChild(table);


            }});

}
initPage();

    

