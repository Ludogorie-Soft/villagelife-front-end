const ava = ({ icon = 'success', toast = false, progressBar = true, text = null, timer = 4000, btnText = 'Okay', direction = 'rtl', position = 'top-right' }) => {
        const modal = document.createElement('section');
        modal.setAttribute('class', 'ava-modal');
        document.body.appendChild(modal);
        const alert = document.createElement('div');
        alert.setAttribute('class', 'ava-alert');
        modal.appendChild(alert);
        var avaIcon;
        if (icon == 'success' && toast == false) {
            avaIcon = `
            <div class="ava-alert__icon" style="background: #96c930; border-top-left-radius: 25px; border-top-right-radius: 25px;">
            <div class="svg-box">
                <svg class="circular green-stroke">
                <circle class="path" cx="75" cy="75" r="50" fill="none" stroke-width="5" stroke-miterlimit="10"/>
            </svg>
            <svg class="checkmark green-stroke">
                <g transform="matrix(0.79961,8.65821e-32,8.39584e-32,0.79961,-489.57,-205.679)">
                    <path class="checkmark__check" fill="none" d="M616.306,283.025L634.087,300.805L673.361,261.53"/>
                </g>
            </svg>
            </div>
        </div>
            `;

        } else if (toast == false && icon == 'none') {
            avaIcon = '';
        }
        document.querySelector('.ava-alert').innerHTML = `
      ${avaIcon}
<div class='ava-text-con'>
        <p class="ava-alert__text">
        ${text}
        </p>
        <button class="ava-alert__btn">${btnText}</button>
    </div>
        `;
        var new_timer_format = '';
        switch (timer) {
            case 1000:
                new_timer_format = '1s';
                break;
            case 2000:
                new_timer_format = '2s';
                break;
            case 3000:
                new_timer_format = '3s';
                break;
            case 4000:
                new_timer_format = '4s';
                break;
            case 5000:
                new_timer_format = '5s';
                break;
            case 6000:
                new_timer_format = '6s';
                break;
            case 7000:
                new_timer_format = '7s';
                break;
            case 8000:
                new_timer_format = '8s';
                break;
            case 9000:
                new_timer_format = '9s';
                break;
            case 10000:
                new_timer_format = '10s';
                break;
            default:
                new_timer_format = '4s';
        }
        if (timer > 10000) {
            timer = 4000;
        }

        if (progressBar == true) {
            const progressBar_el = document.createElement('div');
            progressBar_el.setAttribute('class', 'ava-progress-bar');
            document.querySelector('.ava-alert__btn').appendChild(progressBar_el);
            document.querySelector('.ava-progress-bar').style = `
            animation-duration: ${new_timer_format};
    -webkit-animation-duration: ${new_timer_format};
            `;
        }

        if (progressBar == true) {
            setTimeout(() => {
                modal.remove();
                alert.remove();
            }, timer);
        }

        document.querySelector('.ava-alert__btn').addEventListener('click', function () {
            alert.remove();
            modal.remove();
        })
        window.addEventListener('click', function (e) {
            if (e.target == document.querySelector('.ava-modal')) {
                modal.remove();
                alert.remove();
            }

        })
    };


    if (saveSuccessful) {
        ava({
            icon: 'success',
            text: successMessage,
            btnText: closeButtonText,
            progressBar: true,
            toast: false,
        });
    }


