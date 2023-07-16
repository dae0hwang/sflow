axios({
    url: 'http://3.39.165.43:5510/api/extension/manage/all/list',
    method: 'get',
})
.then(function (response) {
    console.log(response);
    makeTableBody(response.data);
})
.catch(function (error) {
    console.log(error)
});

function makeTableBody(data) {
    let tempTrEle = document.getElementById('temp-tr');
    let previousEle = document.getElementById('default-tr');
    let firstCustom = true;
    for (const info of data) {
        let clone = tempTrEle.cloneNode(true);
        if (firstCustom && info.defaultCheck === false) {
            firstCustom = false;
            previousEle = document.getElementById('custom-tr');
        }
        clone.getElementsByTagName('td')[0].innerText = info.extensionName
        clone.getElementsByTagName('td')[1].innerText = info.defaultCheck
            ? 'O' :'X'
        clone.getElementsByTagName('td')[2].innerText = info.activeCheck
            ? 'O' :'X'
        clone.getElementsByTagName('td')[3].innerText = info.id
        previousEle.after(clone);
        previousEle = clone;
    }
    tempTrEle.remove();
}