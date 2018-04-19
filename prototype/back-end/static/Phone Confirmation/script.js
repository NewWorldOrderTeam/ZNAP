var logoTimeline = anime.timeline({ autoplay: true, direction: 'alternate', loop: true });

logoTimeline
  .add({
  targets: '.checkmark',
  scale: [
    { value: [0, 1], duration: 600, easing: 'easeOutQuad' }
  ]
})
  .add({
  targets: '.check',
  strokeDashoffset: {
    value: [anime.setDashoffset, 0],
    duration: 700,
    delay: 200,
    easing: 'easeOutQuart'
  },
  translateX: {
    value: [6, 0],
    duration: 700,
    delay: 200,
    easing: 'easeOutQuart'
  },
  translateY: {
    value: [-2, 0],
    duration: 700,
    delay: 200,
    easing: 'easeOutQuart'
  },
  offset: 0
});