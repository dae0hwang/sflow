axios({
    url: 'http://3.39.165.43:5510/api/extension/manage/default/extensions',
    method: 'get',
})
.then(function (response) {
    console.log(response);
    makeDefaultList(response.data);
})
.catch(function (error) {
    console.log(error)
});

function makeDefaultList(data) {
    let temp = document.getElementById('default-temp');
    let previousEle = temp;
    for (const info of data) {
        let clone = temp.cloneNode(true);
        let inputEle = clone.firstElementChild;
        inputEle.setAttribute('db-id', info.id);
        if (info.activeCheck === true) {
            inputEle.checked = true;
        } else {
            inputEle.checked = false;
        }
        clone.appendChild(document.createTextNode(' '+info.extensionName));
        previousEle.after(clone);
        previousEle = clone;
    }
    temp.remove();
}