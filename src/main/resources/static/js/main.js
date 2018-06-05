$(function() {
    $('#performSearch').on('click', performSearch);
    $('input').on('change', hideError);
});

function performSearch() {
    var selected = $('.selected-country:checked');
    if (selected.length == 0) {
        showError("Please select a country");
        return;
    }

    var companyCode = $('#companyCode').val().trim();
    if (companyCode == "") {
        showError("Please enter a company code");
        return;
    }

    var url = "http://localhost:8082/toopnode/consumer/provide?country=%s&id=".replace("%s", selected.val()) + companyCode;

    hideError();
    hideResults();
    showSpinner();

    var jqxhr = $.getJSON(url, function(data) {
        showResults(data);
    })
    .fail(function() {
        showError("An error occured or no results");
    })
    .always(function() {
        hideSpinner();
    });

}

function showError(errorMsg) {
    $('#errorMessage').text(errorMsg);
    $('#error').show();
}

function hideError() {
    $('#error').hide();
}

function showSpinner() {
    $('#spinner').addClass('loading');
}

function hideSpinner() {
    $('#spinner').removeClass('loading');
}

function showResults(obj) {
    console.log(obj);
    populateFields(obj);
    populateFields(obj['headOfficeAddres']);

    $('#result').show();
}

function populateFields(obj) {
    for (var prop in obj) {
        if (obj.hasOwnProperty(prop) && $('#result-' + prop).length) {
            $('#result-' + prop).text(obj[prop] ? obj[prop] : 'N/A');
        }
    }
}


function hideResults() {
    $('#result').hide();
}