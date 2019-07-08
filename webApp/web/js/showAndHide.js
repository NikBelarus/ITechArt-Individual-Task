function showIndexPage() {
    document.getElementById('indexPage').style.display = 'block';
}

function hideIndexPage() {
    document.getElementById('indexPage').style.display = 'none';
}

function showGroupOfMainButtons() {
    document.getElementById('groupOfMainButtons').style.display = 'block';
}

function hideGroupOfMainButtons() {
    document.getElementById('groupOfMainButtons').style.display = 'none';
}

function showSearchPage() {
    document.getElementById('searchPage').style.display = 'block';
}

function hideSearchPage(){
    document.getElementById('searchPage').style.display = 'none';
    clearSearchPage();
}

function showEmailPage() {
    document.getElementById('emailPage').style.display = 'block';
}

function hideEmailPage(b) {
    document.getElementById('emailPage').style.display = 'none';
    if(b){
        clearEmailPage();
    }
}

function showContactPage(num) {
    if(num === 0){
        getStandPhoto();
    }
    document.getElementById('contactPage').style.display = 'block';
}

function hideContactPage() {
    document.getElementById('contactPage').style.display = 'none';
    clearContactPage();
}

function clearEmailPage() {
    document.getElementById('recipients').value = null;
    document.getElementById('emHead').value = null;
    document.getElementById('emBody').value = null;
    document.getElementById('template')[0].selected = true;
}

function clearSearchPage() {
    document.getElementById('nameOfContactSearch').value = null;
    document.getElementById('surnameOfContactSearch').value = null;
    document.getElementById('fnameOfContactSearch').value = null;
    document.getElementById('bthOfContactSearch').value = null;
    document.getElementById('dirBirth')[0].selected = true;
    document.getElementById('ctzOfContactSearch').value = null;
    document.getElementById('fstatOfContactSearch')[0].selected = true;
    document.getElementById('sexSearch')[0].selected = true;
    document.getElementById('countryOfContactSearch').value = null;
    document.getElementById('cityOfContactSearch').value = null;
    document.getElementById('streetOfContactSearch').value = null;
    document.getElementById('homeOfContactSearch').value = null;
    document.getElementById('appOfContactSearch').value = null;
    document.getElementById('codeOfContactSearch').value = null;
}

function clearContactPage() {
    document.getElementById('nameOfContact').value = null;
    document.getElementById('surnameOfContact').value = null;
    document.getElementById('fnameOfContact').value = null;
    document.getElementById('bthOfContact').value = null;
    document.getElementById('sex')[0].selected = true;
    document.getElementById('ctzOfContact').value = null;
    document.getElementById('fstatOfContact')[1].selected = true;
    document.getElementById('siteOfContact').value = null;
    document.getElementById('emOfContact').value = null;
    document.getElementById('workOfContact').value = null;
    document.getElementById('countryOfContact').value = null;
    document.getElementById('cityOfContact').value = null;
    document.getElementById('streetOfContact').value = null;
    document.getElementById('homeOfContact').value = null;
    document.getElementById('appOfContact').value = null;
    document.getElementById('codeOfContact').value = null;
    document.getElementById('phones').innerHTML = '';
    document.getElementById('attachmentsBody').innerHTML = '';
    // document.getElementById('formOfContact').innerHTML = '<input type="hidden" id="contact" name="contact">';
    phones = new Phones();
    attachments = new Attachments();
}