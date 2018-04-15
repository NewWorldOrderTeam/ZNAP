var admin_id = localStorage.getItem('admin_id');
var znap_id = localStorage.getItem('znap_id');
var users_response;

function displayUsers(users) {
    $('#list').empty();
    for (var i = 0; i < users.length; i++) {
        $('#list').append('<tr class=\'user\'><th class=\'id\' scope=\'row\'></th><th class=\'name\'></th><' +
            'th class=\'last_name\'></th><th class=\'email\'></th><th  class=\'phone\'></th><' +
            'th class=\'is_closed\'></th><th  class=\'rate\'></th></tr>');
        var id = users[i].id;
        var first_name = users[i].first_name;
        var last_name = users[i].last_name;
        var email = users[i].email;
        var phone = users[i].phone;
        var open_rates = false;
        if (users[i].rates.length > 0) {
            for (var j = 0; j < users[i].rates.length; j++) {
                if (users[i].rates[j].is_closed) {
                    open_rates = true;
                    break;
                }
            }
        }

        $('#list tr:last .id').append(id);
        $('#list tr:last .name').append(first_name);
        $('#list tr:last .last_name').append(last_name);
        $('#list tr:last .email').append(email);
        $('#list tr:last .phone').append(phone);
        if (open_rates) {
            $('#list tr:last .is_closed').append('No');
        } else {
            $('#list tr:last .is_closed').append('Yes');
        }
        if (users[i].rates.length > 0) {
            $('#list tr:last .rate').append(' <button id="dialog-opener" type="button" class="btn btn-primary active ui-butto' +
                'n ui-corner-all ui-widget " onClick="openRates(' + users[i].id + ')">Написати</button>');
        } else {
            $('#list tr:last .rate').append('<button type="button" class ="btn .btn-warning disabled">Немає відгуків</button>');
        }
    }
}

function getUsers(limit_count) {
    getRates();
    var xhr = new XMLHttpRequest();
    var url = 'https://znap.pythonanywhere.com/api/v1.0/web_user/';
    xhr.open('GET', url, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    users_response = JSON.parse(xhr.response);
    var users_count = users_response.count;
    var users = users_response.results;
    var pages_count = Math.ceil(users_count / limit_count);
    var table = document.getElementById('user_table');
    table.insertAdjacentHTML('afterend', '<div id=\'buttons\'></div');
    for (var i = 1; i <= pages_count; i++) {
        console.log(i);
        var pageButtons = '<input type=\'button\' id=\'id' + i + '\'value=\'' + i + '\' onclick=\'changePage(limit_count,'+i+' )\'>';
        document.getElementById('buttons').insertAdjacentHTML('beforeend', pageButtons);
    }
    $('#users_count').text(users_count);
    displayUsers(users);
}


//var table = document.getElementById('user_table');
//var $th = ($hasHead ? table.rows[(0)].outerHTML : ''),
//    $i, $ii, $j = ($hasHead) ? 1 : 0,
//    $tr = [],
//    $hasHead = ($firstRow === 'TH'),
//    $firstRow = table.rows[0].firstElementChild.tagName,
//    $rowCount = 10;




// ($p) is the selected page number. it will be generated when a user clicks a button
//function sort($p) {
//    var users_count = users_response.count;
//    var users = users_response.results;
//    /* create ($rows) a variable to hold the group of rows
//     ** to be displayed on the selected page,
//     ** ($s) the start point .. the first row in each page, Do The Math
//     */
//    var $rows = $th,
//        $s = ((users_count * $p) - users_count);
//    for ($i = $s; $i < ($s + users_count) && $i < $tr.length; $i++)
//        $rows += $tr[$i];
//
//    // create the pagination buttons
//    document.getElementById('buttons').innerHTML = pageButtons(pages_count, $p);
//    // CSS Stuff
//    document.getElementById('id1').setAttribute('class', 'active');
//}

// ($pCount) : number of pages,($cur) : current page, the selected one ..
//function pageButtons(pages_count, $cur) {
//    /* this variables will disable the "Prev" button on 1st page
//       and "next" button on the last one */
//    var $prevDis = ($cur == 1) ? 'disabled' : '',
//        $nextDis = ($cur == pages_count) ? 'disabled' : '',
//        /* this ($buttons) will hold every single button needed
//         ** it will creates each button and sets the onclick attribute
//         ** to the "sort" function with a special ($p) number..
//         */
//        $buttons = '<input type=\'button\' value=\'&lt;&lt; Prev\' onclick=\'sort(' + ($cur - 1) + ')\' ' + $prevDis + '>';
//    for ($i = 1; $i <= pages_count; $i++)
//        $buttons += '<input type=\'button\' id=\'id' + $i + '\'value=\'' + $i + '\' onclick=\'sort(' + $i + ')\'>';
//    $buttons += '<input type=\'button\' value=\'Next &gt;&gt;\' onclick=\'sort(' + ($cur + 1) + ')\' ' + $nextDis + '>';
//    return $buttons;
//}

function getRates() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'https://znap.pythonanywhere.com/api/v1.0/rate/', false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    var rates = JSON.parse(xhr.response);
    $('#rates_count').text(rates.length);
}

var limit_count = 10;
var pages_count;


$('#btnApply').click(function () {
    limit_count = $('#pglmt').val();
    changeLimit(limit_count);
    getUsers();
});


function changeLimit(limit_count) {
    var xhr = new XMLHttpRequest();
    var url = 'https://znap.pythonanywhere.com/api/v1.0/web_user/?limit=' + limit_count;
    xhr.open('GET', url, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    users_response = JSON.parse(xhr.response);
    var users_count = users_response.count;
    pages_count = Math.ceil(users_count / limit_count);
    for (var i = 1; i <= pages_count; i++) {
        console.log(i);
    }
    var users = users_response.results;
    displayUsers(users);
}

function changePage(limit_count, page_number) {
    var xhr = new XMLHttpRequest();
    var offset = limit_count * (page_number - 1);
    var url = 'https://znap.pythonanywhere.com/api/v1.0/web_user/?limit=' + limit_count + '&offset=' + offset;
    xhr.open('GET', url, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    users_response = JSON.parse(xhr.response);
    var users = users_response.results;
    displayUsers(users);
}

function sortUsers(limit_count, page_number, order_by, vector) {
    var xhr = new XMLHttpRequest();
    var offset = limit_count * (page_number - 1);
    var url = 'https://znap.pythonanywhere.com/api/v1.0/web_user/?limit=' + limit_count + '&offset=' + offset + '&ordering='
    if (vector === 'asc') {
        url = url + order_by;
    } else {
        url = url + '-' + order_by;
    }
    xhr.open('GET', url, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    users_response = JSON.parse(xhr.response);
    var users = users_response.results;
    displayUsers(users);
}

function getUser(id) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'https://znap.pythonanywhere.com/api/v1.0/user/' + id + '/', false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    var user = JSON.parse(xhr.response);
    return user;
}

function getCnap(id) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'https://znap.pythonanywhere.com/api/v1.0/znap/' + id + '/', false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    var cnap = JSON.parse(xhr.response);
    return cnap;
}

function getRate() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'https://znap.pythonanywhere.com/api/v1.0/znap/' + znap_id + '/rate/', false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    var total_rate = 0;
    var rates = JSON.parse(xhr.response);
    for (var i = 0; i < rates.length; i++) {
        if (rates[i].quality > 0)
            total_rate++;
        else
            total_rate--;

        $('#list2').append('<li class="rates-item" xmlns="http://www.w3.org/1999/html"><div class="row"> <di' +
            'v class="col-md-6"><h4 class="name"></h4><h6 class="description"></h6></div><div' +
            ' class="col-md-6 text-right"><h4 class="quality"></h4></div></div></li>');

        $('#list2 li:last .name').append(rates[i].first_name + ' ' + rates[i].last_name);
        $('#list2 li:last .quality').append(rates[i].quality);
        $('#list2 li:last .description').append(rates[i].description);
    }
    var result = total_rate >= 0 ?
        '<span style="color:green;"> +' + total_rate + '</span>' :
        '<span style="color:red;"> -' + total_rate + '</span>';
    console.log(result);
    $('.total-rate').append(result);
}

function getAdminRates() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'https://znap.pythonanywhere.com/api/v1.0/admin/' + admin_id + '/rate/', false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    var rates = JSON.parse(xhr.response);
}

function closeRate(id) {
    var closeRate = {
        'is_closed': true
    };

    var xhr = new XMLHttpRequest();
    xhr.open('PUT', 'https://znap.pythonanywhere.com/api/v1.0/rate/' + id + '/', false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(closeRate));
}

function putAdmin(admin_id, rate_id) {
    var adminInRate = {
        admin: admin_id
    };
    var xhr = new XMLHttpRequest();
    xhr.open('PUT', 'https://znap.pythonanywhere.com/api/v1.0/rate/' + rate_id + '/', false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(adminInRate));

}

function openRates(id) {
    for (var i = 0; i < users.length; i++) {
        if (users[i].id == id) {
            for (var j = 0; j < users[i].rates.length; j++) {
                // var cnap = getCnap(users[i].rates[j].znap_id); console.log(cnap);
                $('#rates-modal .rates-content').append('<div class="rate-item"><div class="rate-time">' + new Date(users[i].rates[j].timestamp).getDate() + '.' + (new Date(users[i].rates[j].timestamp).getMonth() + 1) + '.' + new Date(users[i].rates[j].timestamp).getFullYear() + '</div><div class="rate-cnap">Cnap name</div><div class="rate-msg">' + users[i].rates[j].description + '</div><div class="btn rate-btn" target="' + users[i].rates[j].id + '">Message</div></div>');
            }
        }
    }
    $('#wrapper').addClass('blur');
    $('#rates-modal').show();

}

$('#rates-modal .close')
    .on('click', function () {
        $(this)
            .closest('#rates-modal')
            .hide();
        $('#wrapper').removeClass('blur');
        $('#rates-modal .rates-content').empty();
    });
