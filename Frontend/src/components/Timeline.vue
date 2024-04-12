<template>
    <div class="timeline">
        <svg aria-hidden="true" focusable="false" style="width:0;height:0;position:absolute;">
            <linearGradient id="icon-gradient">
                <stop offset="0%" stop-color="var(--color-accent-light)" />
                <stop offset="100%" stop-color="var(--color-accent-dark)" />
            </linearGradient>
            <defs>
                <symbol id="icon-brain" viewBox="0 0 66 66">

                </symbol>
                <symbol id="icon-bulb" viewBox="0 0 512 512">
                </symbol>
                <symbol id="icon-customers" viewBox="0 0 64 64">

                </symbol>
                <symbol id="icon-rocket" viewBox="0 0 494.929 494.929">

                </symbol>
                <symbol id="icon-seo" viewBox="0 0 60 60">

                </symbol>
                <symbol id="icon-target" viewBox="0 0 4335 4335">

                </symbol>
            </defs>
        </svg>
        <!--END SVG GRADIENT PART-->

        <div class="content">
            <div class="container">

                <div class="timeline">
                    <div class="timeline__stepper">
                        <div class="timeline__step is-active">

                            <p class="timeline__step-title">

                            </p>
                        </div>
                        <div class="timeline__step">





                            <p class="timeline__step-title">

                            </p>
                        </div>
                        <div class="timeline__step">

                            <p class="timeline__step-title">

                            </p>
                        </div>
                        <div class="timeline__step">

                            <p class="timeline__step-title">

                            </p>
                        </div>
                        <div class="timeline__step">

                            <p class="timeline__step-title">

                            </p>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>



</script>

<style>
:root {
    --color-accent-light: #00DBDE;

    --color-secondary: #ccc;

    --text-color-default: #2c2c2c;
    --text-color-light: #fff;

    --font-size-default: 10px;
    --font-size-lg: 15px;

    --font-weight-regular: 400;
    --font-weight-semibold: 600;
    --font-weight-default: var(--font-weight-regular);

    --border-color-default: var(--color-secondary);
    --border-color-accent: var(--color-accent-dark);

    --bg-color-default: #fff;
    --bg-color-dark: #2c2c2c;
    --bg-color-accent--lignt: var(--color-accent-light);
    --bg-color-accent--dark: var(--color-accent-dark);

    --transition-duration: .2s;
    --transition-timing-function: linear;
    --transition-delay: 0s;

    --negative-multiplier: -1;

    --gradient-default: linear-gradient(122deg, var(--color-accent-light) 0%, var(--color-accent-dark) 100%);

}







/* TIMELINE STYLES: YOU NEED THEM!!! */
.timeline {


    width: 90%;
    max-width: var(--wrapper-width);
    margin: auto;
}

.timeline__stepper {
    --step-border-width: 3px;


    position: relative;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 70px;
    margin-bottom: var(--offset-to-slider-content);
}

@media(min-width: 530px) {
    .timeline__stepper {
        display: flex;
        justify-content: space-around;
        gap: 0;
    }
}

@media(min-width: 700px) {
    .timeline__stepper {
        --offset-to-slider-content: 50px;
    }
}

.timeline__step {
    position: relative;
    width: 100%;
    cursor: pointer;
}

@media(min-width: 530px) {
    .timeline__step {
        width: 100%;
        ;
    }
}



@media(min-width: 700px) {
    .timeline__icon {
        --timeline-icon-dimensions: 60px;

        margin-bottom: 30px;
    }
}


.timeline__step-title {
    --timeline-dot-dimensions: 11px;
    --extra-offset-fox-pixelperfect: -1px;

    position: relative;
    display: none;
    padding-top: 30px;
    margin-bottom: 0;
    text-align: center;
    line-height: 1.7;
    border-top: var(--step-border-width) solid var(--border-color-default);
}

@media(min-width: 700px) {
    .timeline__step-title {
        display: block;
    }
}

.timeline__step-title:before,
.timeline__step-title:after {
    position: absolute;
    top: var(--dot-top-position);
    left: 50%;
    display: block;
    width: var(--timeline-dot-dimensions);
    height: var(--timeline-dot-dimensions);
    content: '';
    border-radius: 50%;
}

.timeline__step-title:before {
    --dot-top-position: calc(var(--timeline-dot-dimensions)/2 * var(--negative-multiplier) + var(--extra-offset-fox-pixelperfect));

    transform: translate(-50%);
    border: 2px solid var(--border-color-default);
    border-radius: 50%;
    background-color: #fff;
    z-index: 5;
}


/* this elements generates in JS */


@media(min-width: 700px) {
    .timeline__step-active-marker {
        display: block;
    }
}

.timeline__step.is-active {
    cursor: default;
}



.timeline__step.is-active .timeline__step-title:before {
    transition: background-color var(--transition-duration) var(--transition-timing-function) var(--transition-delay);
    background-color: var(--color-accent-dark);
    border-color: var(--border-color-accent);
}



.timeline__step.is-active .timeline__icon--default,
.timeline__step:not(.is-active):hover .timeline__icon--default {
    opacity: 0;
}

.timeline__step.is-active .timeline__icon--active,
.timeline__step:not(.is-active):hover .timeline__icon--active {
    opacity: 1;
}
</style>