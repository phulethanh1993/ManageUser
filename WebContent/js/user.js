function showOrHideElements(element) {
    if (document.getElementById(element).style.display == "none") {
    document.getElementById(element).style.display = "block";
    } else {
    document.getElementById(element).style.display = "none";
    }
}
function goBack() {
    window.history.back();
}
function goTo(url) {
    window.location.href = url;

}
function createHiddenParam() {
    var groupId = document.getElementById("groupId");
    var groupName = groupId.options[groupId.selectedIndex].innerHTML;

    var codeLevel = document.getElementById("codeLevel");
    var nameLevel = codeLevel.options[codeLevel.selectedIndex].innerHTML;

    var inputGroupName = document.createElement("input");
    inputGroupName.setAttribute("type", "hidden");
    inputGroupName.setAttribute("name", "groupName");
    inputGroupName.setAttribute("value", groupName);
    document.getElementById("inputform").appendChild(inputGroupName);

    var inputNameLevel = document.createElement("input");
    inputNameLevel.setAttribute("type", "hidden");
    inputNameLevel.setAttribute("name", "nameLevel");
    inputNameLevel.setAttribute("value", nameLevel);
    document.getElementById("inputform").appendChild(inputNameLevel);
}
