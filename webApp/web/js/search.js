function searchContact() {
    var name = null;
    if(document.getElementById('nameOfContactSearch').value.length) {
        name = document.getElementById('nameOfContactSearch').value;
    }
    var surName = null;
    if(document.getElementById('surnameOfContactSearch').value.length) {
        surName = document.getElementById('surnameOfContactSearch').value;
    }
    var fathersName = null;
    if(document.getElementById('fnameOfContactSearch').value.length) {
        fathersName = document.getElementById('fnameOfContactSearch').value;
    }
    var birthdate = null;
    if(document.getElementById('bthOfContactSearch').value.length) {
        birthdate = document.getElementById('bthOfContactSearch').value;
    }
    var sex = document.getElementById('sexSearch').value;
    var citizenship = null;
    if(document.getElementById('ctzOfContactSearch').value.length) {
        citizenship = document.getElementById('ctzOfContactSearch').value;
    }
    var familyStatus = document.getElementById('fstatOfContactSearch').value;
    var country = null;
    if(document.getElementById('countryOfContactSearch').value.length) {
        country = document.getElementById('countryOfContactSearch').value;
    }
    var city = null;
    if(document.getElementById('cityOfContactSearch').value.length) {
        city = document.getElementById('cityOfContactSearch').value;
    }
    var street = null;
    if(document.getElementById('streetOfContactSearch').value.length) {
        street = document.getElementById('streetOfContactSearch').value;
    }
    var homeNum = null;
    if(document.getElementById('homeOfContactSearch').value.length) {
        homeNum = Number(document.getElementById('homeOfContactSearch').value);
    }
    var apartment = null;
    if(document.getElementById('appOfContactSearch').value.length) {
        apartment = Number(document.getElementById('appOfContactSearch').value);
    }
    var postcode = null;
    if(document.getElementById('codeOfContactSearch').value.length) {
        postcode = document.getElementById('codeOfContactSearch').value;
    }
    if(document.getElementById('dirBirth').value === 'more'){
        birthdate += '+';
    }

    fetch('api/contacts_ids', {
        method: 'POST',
        headers: {
            'content-type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify({name:name, surName:surName, fathersName:fathersName, birthdate:birthdate,
            sex:sex, citizenship:citizenship, familyStatus:familyStatus, webSite:null, email:null,
            currentWorkspase:null, photo:null, address:{country:country, city:city,
                street:street, homeNum:homeNum, apartment:apartment, postcode:postcode}})
    }).then(function (res) {
        return res.json();
    }).then(function (data) {
        hideSearchPage();
        showGroupOfMainButtons();
        ids = data;
        displayContacts(1);
    })
}