function showMyModalForPhone() {
    document.getElementById('myPhoneModal').style.display = "block";
}

function hideMyModalForPhone() {
    document.getElementById('myPhoneModal').style.display = "none";
    clearPhoneFields();
}

function showMyModalForAttachment() {
    document.getElementById('myAttachModal').style.display = "block";
}

function hideMyModalForAttachment() {
    document.getElementById('myAttachModal').style.display = "none";
    clearAttachFields();
}

function showMyDangerPhoneModal() {
    document.getElementById('myDangerPhoneModal').style.display = "block";
}

function hideMyDangerPhoneModal() {
    document.getElementById('myDangerPhoneModal').style.display = "none";
}

function showMyDangerAttachmentModal() {
    document.getElementById('myDangerAttachModal').style.display = "block";
}

function hideMyDangerAttachmentModal() {
    document.getElementById('myDangerAttachModal').style.display = "none";
}

function showMyDangerContactModal() {
    document.getElementById('myDangerContactModal').style.display = "block";
}

function hideMyDangerContactModal() {
    document.getElementById('myDangerContactModal').style.display = "none";
}

function showInformationMessage(title, message) {
    document.getElementById('infoHead').innerHTML = '<h5 class="display-4 mb-4">' + title + '</h5>';
    document.getElementById('infoBody').innerHTML = message;
    document.getElementById('myInputWarning').style.display = "block";
}

function hideInformationMessage() {
    document.getElementById('myInputWarning').style.display = "none";
}