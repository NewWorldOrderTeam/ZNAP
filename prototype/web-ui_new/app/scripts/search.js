function Search() {
  // Declare variables
  var input,
    filter,
    table,
    tr,
    td,
    i;
  input = document.getElementById('myInput');
  filter = input
    .value
    .toUpperCase();
  table = document.getElementById('user_table');
  tr = table.getElementsByClassName('user');

  // Loop through all table rows, and hide those who don't match the search query
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName('th')[1];
    if (td) {
      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = '';
      } else {
        tr[i].style.display = 'none';
      }
    }
  }
}