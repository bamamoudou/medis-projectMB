$(() => {
    $('.btn_tab').on('click', function(){
        $($(this).attr('data-btn-target-class')).addClass('none').removeClass('block');
        $($(this).attr('data-btn-target-id')).addClass('block').removeClass('none');
        $('.btn_tab').removeClass('active');
        $(this).addClass('active');
    });

    $('#visit_form').on('submit', function(e){
        e.preventDefault();
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8888' + $(this).data('url'),
            data: { doctorName : $('#doctorName').val(), content : $('#content').val(), active: true},
            success : function(data, status) {
                let createDateStr = '';
                let lastDateChangeStr = '';

                if(data.createDate != null && data.createDate !== '') {
                    const createDate = new Date(data.createDate);
                    createDateStr = createDate.getDate();
                    createDateStr += '/';
                    createDateStr += createDate.getMonth();
                    createDateStr += '/';
                    createDateStr += createDate.getFullYear();
                    createDateStr += ' ';
                    createDateStr += createDate.getHours();
                    createDateStr += ':';
                    createDateStr += createDate.getMinutes();
                    createDateStr += ':';
                    createDateStr += createDate.getSeconds();
                }
                if(data.lastChangeDate != null && data.lastChangeDate !== '') {
                    const lastChangeDate = new Date(data.lastChangeDate);
                    lastDateChangeStr = lastChangeDate.getDate();
                    lastDateChangeStr += '/';
                    lastDateChangeStr += lastChangeDate.getMonth();
                    lastDateChangeStr += '/';
                    lastDateChangeStr += lastChangeDate.getFullYear();
                    lastDateChangeStr += ' ';
                    lastDateChangeStr += lastChangeDate.getHours();
                    lastDateChangeStr += ':';
                    lastDateChangeStr += lastChangeDate.getMinutes();
                    lastDateChangeStr += ':';
                    lastDateChangeStr += lastChangeDate.getSeconds();
                }

                let visit = '<div class="visit wdth_100 flex flex_row pos_relative active">';
                    visit += '<div class="visit_info flex flex_column hght_100 wdth_35">';
                    visit += '<p class="visit_info_doctor txt_ltr-sp_2">Dr. ' + data.doctorName + '</p>';


                if(createDateStr != null && createDateStr !== '') {
                    visit += '<div class="visit_info_ctn">';
                    visit += '<p class="color_black txt_ltr-sp_2 txt_upp">Date :</p>';
                    visit += '<p class="color_greyCA txt_ltr-sp_2">' + createDateStr +'</p>';
                    visit += '</div>';
                }

                if(lastDateChangeStr != null && lastDateChangeStr !== '') {
                    visit += '<div class="visit_info_ctn">';
                    visit += '<p class="color_black txt_ltr-sp_2 txt_upp">Last change :</p>';
                    visit += '<p class="color_greyCA txt_ltr-sp_2">' + lastDateChangeStr +'</p>';
                    visit += '</div>';
                }

                visit += '</div>';
                visit += '<p class="visit_content hght_100 wdth_65 txt_ltr-sp_2">' + data.content.replace(/\n/g, "</br>") + '</p>';
                visit += '<span data-id="' + data.id + '" data-active="' + !data.active + '" class="btn btn_active pos_absolute flex_center">';
                visit += '<svg class="feather strk_red">';
                visit += '<use href="/assets/icons/feather-sprite.svg#x"/>';
                visit += '</svg>';
                visit += '</span>';
                visit += '</div>';

                $('#visit_form').after(visit);
            },
            error : function(data, status){
                $('#form_error_msg').text(data.responseJSON.message);
            }
        });
    });

    $('.btn_active').on('click', function(){
        console.log('OK');
        let el = $(this);
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8888/medical-record/update?id=' + el.data('id') + '&patientId=' + el.data('patient') + '&active=' + el.data('active'),
            success : function(data, status) {
                el.parent().toggleClass('active');
                let icon = ''
                if(el.parent().hasClass('active')) {
                    icon += '<svg class="feather strk_red">';
                    icon += '<use href="/assets/icons/feather-sprite.svg#x"/>';
                    icon += '</svg>';
                } else {
                    icon += '<svg class="feather strk_green">';
                    icon += '<use href="/assets/icons/feather-sprite.svg#check"/>';
                    icon += '</svg>';
                }
                el.html(icon);
            },
            error : function(data, status){
                $('#form_error_msg').text(data.responseJSON.message);
            }
        });
    })
})