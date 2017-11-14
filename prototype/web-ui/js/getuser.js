function getUser(){
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "http://znap.pythonanywhere.com/api/v1.0/user/", false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    users = JSON.parse(xhr.response);
    console.log(users);
}

function getRate() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "http://znap.pythonanywhere.com/api/v1.0/rate/", false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    rates = JSON.parse(xhr.response);

    for (var i in rates){
        console.log(rates[i].description);
        $('#list').append("<li><p class='dasd'></p> </li>");
        $('#list li:last .dasd').append(rates[i].description);
    }
    console.log(rates);
}