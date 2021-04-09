$(() => {
    $('#search').on('input', function(){
        $('.dataset_el_ctn').html('');
        if ($(this).val() !== '') {
            $.get('http://localhost:8080/patient/search?search=' + $(this).val(), function (data) {
                $.each(data, function (index, element) {
                    let patient = '<a href="/patient/' + element.id + '" class="dataset_el flex flex_row flex_align_center pos_relative">';
                    patient += '<div class="dataset_el_icon flex_center oflow_hidden">';

                    if(element.profilePictureExt !== null) {
                        patient += '<img src="/assets/profilePicture/' + element.id + '_' + element.firstname + '_' + element.lastname + element.profilePictureExt + '"></div>';
                    } else {
                        patient += '<img src="/assets/icons/user.svg"></div>';
                    }

                    patient += '<p class="dataset_el_lastname txt_upp">' + element.lastname + '</p>';
                    patient += '<p class="dataset_el_firstname txt_captz">' + element.firstname + '</p>';
                    patient += '<svg class="icon feather strk_grey96 pos_absolute"><use href="/assets/icons/feather-sprite.svg#chevron-right"/></svg>'
                    patient += '</a>'

                    $('.dataset_el_ctn').append(patient);
                });
            })
        }
    })
})