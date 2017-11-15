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
        $('#list').append("<tr>" + 
            "<th class='id' scope='row'></th>" +
            "<th class='name'></th>" +
            "<th class='middle_name'></th>" +
            "<th class='last_name'></th>" +
            "<th class='email'></th>" +
            "<th class='phone'></th>" +
            "</tr>");
        user = getUser(rates[i].user_id);
        console.log(user);
        first_name = user.first_name;
        last_name = user.last_name;
        middle_name = user.middle_name;
        email = user.email;
        phone = user.phone;
        $('#list tr:last .id').append(i);
        $('#list tr:last .name').append(first_name);
        $('#list tr:last .middle_name').append(middle_name);
        $('#list tr:last .last_name').append(last_name);
        $('#list tr:last .email').append(email);
        $('#list tr:last .phone').append(phone);

        $('#list2').append('<li class="list-group-item" xmlns="http://www.w3.org/1999/html">' +
            '<div class="row"> ' +
            '<div class="col-md-6"><h4 class="name"></h4><h6 class="description"></h6></div>' +
            '<div class="col-md-6 text-right"><h4 class="quality"></h4><h6 class="time"></h6></div>' +
            '</div>' +
            '</li>');

        $('#list2 li:last .name').append(first_name+' '+last_name);
        $('#list2 li:last .quality').append(rates[i].quality);
        $('#list2 li:last .time').append(rates[i].dialog[0].timeStamp);
        $('#list2 li:last .description').append(rates[i].description);

        console.log(rates[i].dialog[0].timeStamp)
    }
} 