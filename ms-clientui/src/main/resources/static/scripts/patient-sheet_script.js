$(() => {
    $('.btn_tab').on('click', function(){
        $($(this).attr('data-btn-target-class')).addClass('none').removeClass('block');
        $($(this).attr('data-btn-target-id')).addClass('block').removeClass('none');
        $('.btn_tab').removeClass('active');
        $(this).addClass('active');
    });
})