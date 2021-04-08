class Template {
    initTemplate(){

    }

    toggleCheckbox(input_chk_field, input_chk_btn) {
        $(input_chk_btn).on('click', function(){
            $(input_chk_btn).toggleClass('on').toggleClass('off');
            if ($(input_chk_field).attr("checked") !== undefined){
                $(input_chk_field).removeAttr("checked")
            } else {
                $(input_chk_field).attr("checked", "checked")
            }
            console.log('toggle');
        });
    }

    changeRadio(input_radio, slc_class, input_text) {
        $(input_radio).on('click', function(){
            $(input_radio).each(function(){
                $(this).removeClass(slc_class);
            })
            $(this).addClass(slc_class);
            $(input_text).val($(this).attr('data-value'));
        })
    }

    changeTab(section_class, btn_class, slc_section_class, slc_btn_class) {
        $(btn_class).on('click', function(){
            $(section_class).each(function(){
                $(this).removeClass(slc_section_class);
            })

            $(btn_class).each(function(){
                $(this).removeClass(slc_btn_class);
            })

            $(this).addClass(slc_btn_class);
            $('#' + $(this).attr('data-targetID')).addClass(slc_section_class);
        })
    }

    changeSelect(slc, active) {
        if (active) {
            $(slc).removeClass('unselect').addClass('selected');
        } else {
            $(slc).addClass('unselect').removeClass('selected');
        }
    }

    enabledForm(form_selector, btn_selector) {
        $(btn_selector).on('click', function(){
            if ($(form_selector).hasClass('disabled')) {
                $(form_selector + ' input').each(function(){
                    $(this).attr('disabled', false);
                })

                $(form_selector + ' .input_select').each(function(){
                    $(this).removeClass('disabled');
                })

                $(form_selector).removeClass('disabled');
            } else {
                $(form_selector + ' input').each(function(){
                    $(this).attr('disabled', true);
                })

                $(form_selector + ' .input_select').each(function(){
                    $(this).addClass('disabled');
                })

                $(form_selector).addClass('disabled');
            }

            $(form_selector + ' .input_select span').each(function(){
                $(this).toggleClass('c_greyE1').toggleClass('c_black');
            })

            $(form_selector + ' .input_select .feather').each(function(){
                $(this).toggleClass('f_greyE1').toggleClass('f_black');
            })

            $(form_selector + ' .input_ctn.btns').each(function(){
                $(this).removeClass('disable');
            })

            $(this).parent().addClass('disable');
        })
    }

    openElement(element, open_btn, open_class, close_class){
        $(open_btn).on('click', function(){
            $(element).addClass(open_class).removeClass(close_class);
        });
    }

    closeElement(element, close_btn, open_class, close_class){
        $(close_btn).on('click', function(){
            $(element).removeClass(open_class).addClass(close_class);
        });
    }
}

class FormValidator {
    validateEmail(sEmail) {
        const filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
        return filter.test(sEmail)
    }
}

const template = new Template();
const formValidator = new FormValidator();

$(() => {
    template.initTemplate();
})