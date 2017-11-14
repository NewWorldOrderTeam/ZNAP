getRate();

function getUser(id){
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "http://znap.pythonanywhere.com/api/v1.0/user/"+id+"/", false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    user = JSON.parse(xhr.response);
    return user;
}

function getRate() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "http://znap.pythonanywhere.com/api/v1.0/rate/", false);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    rates = JSON.parse(xhr.response);

    for (var i in rates){
        $('#list').append("<li class='list-group-item'>" +
            "<h4 class='user'></h4>" +
            "<h5 class='rate'></h5> " +
            "</li>");
        user = getUser(rates[i].user_id);
        console.log(user);
        first_name = user.first_name;
        last_name = user.last_name;
        $('#list li:last .user').append(first_name+' '+ last_name);
        $('#list li:last .rate').append(rates[i].description);
        console.log(rates[i].dialog[0].timeStamp)
    }
}