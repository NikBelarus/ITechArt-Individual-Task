function pagination(contactsPerPage, totalContacts) {
    const pageNumbers = [];

    for(var i = 1; i <= Math.ceil(totalContacts/contactsPerPage); i++){
        pageNumbers.push(i);
    }

    var pattern = '<nav><ul class="pagination">{{#.}}<li class="page-item"><a onclick="displayContacts({{.}})" href="#" class="page-link">{{.}}</a></li>{{/.}}</ul></nav>';

    var output = Mustache.render(pattern, pageNumbers);
    document.getElementById('pagination').innerHTML = output;
}

function changePagination() {
    contactsPerPage = Number(document.getElementById('paginationNum').value);
    displayContacts(1);
}