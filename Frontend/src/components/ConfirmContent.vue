<template>
  <button style="width: 90px;" class="button-hold" :style="`--duration: ${duration}ms`" @mousedown="start"
    @touchstart="start" @keypress="start" @mouseup="stop" @mouseout="stop" @touchend="stop" @keyup="stop">
    <div>
      <svg class="icon" viewBox="0 0 16 16">
        <polygon points="1.3,6.7 2.7,8.1 7,3.8 7,16 9,16 9,3.8 13.3,8.1 14.7,6.7 8,0"></polygon>
      </svg>
      <svg class="progress" viewBox="0 0 32 32">
        <circle r="8" cx="16" cy="16" />
      </svg>
      <svg class="tick" viewBox="0 0 24 24">
        <polyline points="18,7 11,16 6,12" />
      </svg>
    </div>
    {{ label }}
  </button>
</template>

<script>
export default {
  props: {
    duration: {
      type: Number,
      default: 1600
    },
    label: {
      type: String,
      default: 'Publish'
    }
  },
  methods: {
    start(ev) {
      if (ev.type !== 'keypress' || (ev.type === 'keypress' && ev.which === 32 && !this.$el.classList.contains('process'))) {
        this.$el.classList.add('process');
        this.timeout = setTimeout(this.success, this.duration, this.$el);
      }
    },
    stop(ev) {
      if (ev.type !== 'keyup' || (ev.type === 'keyup' && ev.which === 32)) {
        this.$el.classList.remove('process');
        clearTimeout(this.timeout);
      }
    },
    success(button) {
      button.classList.add('success');
    }
  }
}
</script>


<style scoped>
.button-hold {
  --color: #f6f8ff;
  --background: #000000;
  --icon: var(--color);
  --progress-border: #05638a;
  --progress-active: #fff;
  --progress-success: #05638a;
  --tick-stroke: var(--progress-active);
  --shadow: rgba(0, 9, 61, 0.2);
  font-size: 20px;
  font-weight: 500;


  padding: 12px 20px 12px 12px;
  border: 0;
  border-radius: 24px;
  outline: none;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  cursor: pointer;
  -webkit-backface-visibility: hidden;
  -webkit-appearance: none;
  transition: transform 0.3s, box-shadow 0.3s;
  box-shadow: 0 var(--shadow-y, 4px) var(--shadow-blur, 12px) var(--shadow);
  transform: scale(var(--scale, 1)) translateZ(0);
  color: var(--color);
  background: var(--background);
}

.button-hold>div {
  margin-right: 4px;
  border-radius: 50%;
  display: inline-block;
  vertical-align: top;
  position: relative;
  background: var(--progress-border);
}

.button-hold>div:before {
  content: '';
  width: 16px;
  height: 16px;
  left: 2px;
  top: 2px;
  z-index: 1;
  position: absolute;
  background: var(--background);
  border-radius: inherit;
  transform: scale(var(--background-scale, 1));
  transition: transform 0.32s ease;
}

.button-hold>div svg {
  display: block;
}

.button-hold>div svg.icon,
.button-hold>div svg.tick {
  position: absolute;
}

.button-hold>div svg.icon {
  width: 8px;
  height: 8px;
  left: 6px;
  top: 6px;
  fill: var(--icon);
  z-index: 1;
  transition: opacity 0.2s, transform 0.2s;
  opacity: var(--icon-opacity, 1);
  transform: translateY(var(--icon-y, 0)) scale(var(--icon-scale, 1));
}

.button-hold>div svg.progress,
.button-hold>div svg.tick {
  fill: none;
}

.button-hold>div svg.progress {
  width: 20px;
  height: 20px;
  transform: rotate(-90deg) scale(var(--progress-scale, 1));
  transition: transform 0.5s ease;
}

.button-hold>div svg.progress circle {
  stroke-dashoffset: 1;
  stroke-dasharray: var(--progress-array, 0) 52;
  stroke-width: 16;
  stroke: var(--progress-active);
  transition: stroke-dasharray var(--duration) linear;
}

.button-hold>div svg.tick {
  width: 20px;
  height: 20px;
  left: 0;
  top: 0;
  stroke-width: 3;
  stroke-linecap: round;
  stroke-linejoin: round;
  stroke: var(--tick-stroke);
  transition: stroke 0.3s ease 0.7s;
}

.button-hold>div svg.tick polyline {
  stroke-dasharray: 18 18 18;
  stroke-dashoffset: var(--tick-offset, 18);
  transition: stroke-dashoffset 0.4s ease 0.7s;
}

.button-hold:focus:not(.process),
.button-hold:hover:not(.process) {
  --shadow-y: 8px;
  --shadow-blur: 16px;
}

.button-hold:active:not(.success) {
  --scale: 0.96;
  --shadow-y: 4px;
  --shadow-blur: 8px;
}

.button-hold.process,
.button-hold.success {
  --progress-array: 52;
  --icon-y: -4px;
  --icon-scale: 0.6;
  --icon-opacity: 0;
}

.button-hold.success {
  --progress-border: none;
  --progress-scale: 0.11;
  --tick-stroke: var(--progress-success);
  --background-scale: 0;
  --tick-offset: 36;
}

.button-hold.success>div svg.progress {
  -webkit-animation: tick 0.3s linear forwards 0.4s;
  animation: tick 0.3s linear forwards 0.4s;
}

@-webkit-keyframes tick {
  100% {
    transform: rotate(-90deg) translate(0, -5px) scale(var(--progress-scale));
  }
}

@keyframes tick {
  100% {
    transform: rotate(-90deg) translate(0, -5px) scale(var(--progress-scale));
  }
}</style>