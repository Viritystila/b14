(ns b14 (:use [trigger.trigger] [trigger.synths] [overtone.core]) (:require [viritystone.tone :as t]) )


(trg :snare snare :in-trg [r] [r] [r]  [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]] [r] [r] [r] :in-amp [2])

(trg :kick kick :in-trg  [1 r 1 r] [1 [(repeat 4 1)]] [r] [1 r 2 [1 1]] :in-f2 [80 60] :in-amp [0.01])

(trg :tb303 tb303 :in-trg [1 1 1 1] [(repeat 8 1)] :in-amp [0.4] :in-note [22] [20] [20]  :in-gate-select [0] [1] :in-attack [0.001] :in-decay [0.9] :in-sustain [0.5] :in-release [0.3] :in-r [0.9] :in-cutoff [2500 2000] [2000] :in-wave [1])

(trg :super   supersaw :in-freq [(range 50 66 1)] [66] [66] [(range 66 50 -1)] [50] [50] :in-amp [0.38223])

(stp :tb303)

(stp :kick)

(stp  :snare)

(stp :super)

(stp :flute)

(stp :flute2)

(stp :lead)

(stp :lead2)

(trg :kick kick :in-trg [1 [2 [3 4 5 [6 [7 8 9 10]]]]] (repeat 3 [1 2]) [1 2 3 4]  (repeat 3 [1 2])  :in-f1 [(range 100 500 100)] [100 200] :in-amp [0.03]:in-f2 [80 60] )

(trg :kick kick :in-trg [[1 [2 [3 4 5 [6 [7 8 9]]]]] ] [(range 10 18 1) (repeat 8 1)] :in-f1 [300] :in-f2 [100] :in-f3 [100]  :in-amp [0.02] )

(trg :kick
     kick
     :in-trg [(repeat 8 1)] [1 1 1 1 1 1 1 [1 1]]
     :in-f1 [(range 100 180 10)] [(range 180 100 -10)]
     :in-f2 [100]
     :in-f3 [20]
     :in-amp [0.02] )


(trg :snare snare :in-trg [1 1 5 r 1] :in-amp [1] )

(trg  :mooger  mooger :in-trg [1] :in-amp [1] :in-note [60] [59] [61] [62 61 59 58] [60 [59 62]] [61] [62] [61])

(stp :mooger)

(stp :mooger2)

(trg :mooger
    mooger
    :in-trg [1 1 1 1] [1] [1]
    :in-amp [3]
    :in-note [30 31 32 30] [30 40 30 40] [40] [30]
    :in-gate-select [1]
    :in-osc1 [1 1 0 0]
    :in-osc2 [2 2 1 1]
    :in-attack [0.0021]
    :in-decay [0.95]
    :in-sustain [0.4]
    :in-release [0.03]
    :in-cutoff  [(range 1000 200 -200) (range 100 900 100)]
    :in-fattack [0.022]
    :in-fdecay [0.9]
    :in-fsustain [0.9]
    :in-frelease [0.01]
    :in-osc2-level [2]
    :in-osc1-level [1])




(trg :tb303
    tb303
    :in-trg [ 1 [1 [1 [1 [1 [1 [1 1]]]]]]] [(repeat 8 1)]  [1 1 1 1]  [1 1 1 1]
    :in-amp [1]
    :in-note [22] [20] [20]
    :in-gate-select [1] [0]
    :in-attack [0.001]
    :in-decay [0.9]
    :in-sustain [0.5]
    :in-release [0.3]
    :in-r [0.9]
    :in-cutoff [2500 2500] [2000 1000] [(range 1000 100 -100)]
    :in-wave [1])



(trg :kick
    kick
    :in-trg [r 1] [r] [1 1] [r] [1 [1 1 1 1 ]] [r] [1 1 [1 1] r]
    :in-f3 [100]
    :in-amp [0.0001]
    :in-f2 [80])


(trg :flute
    simple-flute
    :in-trg [1 1 1 1]
    :in-freq (repeat 8 [880]) (repeat 8 [800])
    :in-gate-select (repeat 4 [0]) (repeat 4 [1])
    :in-attack [0.001]
    :in-decay  [0.91]
    :in-sustain [0.75]
    :in-release [ 0.09]
    :in-amp [1.0]
    :in-ctrl-select [1])

(stp :flute)


(trg :flute
    simple-flute
    :in-trg [1 1 1 1]
    :in-gate-select [0]
    :in-freq (repeat 8 [588]) (repeat 8 [1800])
    :in-amp [0.9])


(trg :lead
    cs80lead
    :in-trg [1]
    :in-freq (repeat 8 [1880]) (repeat 8 [800])
    :in-vibrate [5] [6] [7] [8] [10] [12] [14] [20] [25] [30] [14] [12] [10] [8] [5] [2] [1] [0]
    :in-amp [0.29])


(trg :lead2
    cs80lead
    :in-trg [1]
    :in-freq (repeat 5 [440]) [(take 14 (cycle [440 880]))]
    :in-vibrate [15] [16] [17] [18] [19] [18] [17] [16] [15] [14] [10] [4] (repeat 5 [0]) [19] [18] [17] [16] [15] [14]
    :in-dtune [0.2] [0.1] [0.01]
    :in-amp [0.39])

(stp :lead2)


(trg :bow
     bowed
     :in-trg [1] [1 r 1 1] [1]
     :in-amp [0.2]
     :in-note [(chord-degree :i :c1 :ionian)] [(note :c1)] [(note :d2)] [(note :e1)]
     :in-velocity [50]
     :in-gate-select [0]
     :in-bow-offset [0.01]
     :in-bow-position [1.75]
     :in-bow-slope [0.8]
     :in-vib-freq [6.127]
     :in-vib-gain [1.19] )

(trg :ks1
     ks1
     :in-trg [1 1 1 1] [[1 1] r r [1 1]] [r] [1 r [1 [1 1]] 1]
     :in-dur [4.1]
     :in-amp [1]
     :in-note [(chord-degree :i :d4 :ionian)]
     :in-decay [30.1]
     :in-coef [(range 0.01 0.6 0.01)]  )


(trg :vb
     vintage-bass
     :in-trg [ 1 1 1 1] [1 [1 1]] [1 ] [1 1 1  1]
     :in-gate-select [1]
     :in-amp [2]
     :in-note [(chord-degree :i :d4 :ionian)]  (repeat 3 [(note :d4)])
     :in-a [0.01])

(stp :bow)

                                        ;Video
(t/start "./b14.glsl" :width 1920 :height 1080 :cams [0 1] :videos ["../videos/tietoisku_1_fixed.mp4" "../videos/spede_fixed.mp4"  "../videos/vt2.mp4" "../videos/hapsiainen_fixed.mp4" "../videos/sormileikit.mp4"])

(lss)



;(defonce beat-cnt-bus-atom_1 (bus-monitor b1st_beat-cnt-bus))



(def ldf  (:trigger-value-bus (:in-freq (:triggers (:lead @synthConfig)))))

(def ld2v  (:trigger-value-bus (:in-vibrate (:triggers (:lead2 @synthConfig)))))

(def ld  (:trigger-value-bus (:in-vibrate (:triggers (:lead @synthConfig)))))

(def ldm (control-bus-monitor ld))

@ldm

(def snaretrig  (:trigger-value-bus (:in-trg (:triggers (:snare @synthConfig)))))


(def kickttrig5  (:trigger-value-bus (:in-trg (:triggers (:kick @synthConfig)))))

(def ktm5 (control-bus-monitor kickttrig5))

@ktm5

(def tbtv9  (:trigger-value-bus (:in-trg (:triggers (:kick @synthConfig)))))

(def tbmnt9 (control-bus-monitor tbtv9))

(nth  (control-bus-get kickttrig5) 0)

(on-trigger (get-trigger-val-id :kick :in-trg) (fn [val] (t/set-dataArray-item 0 val )) ::kick )

(on-trigger (get-trigger-val-id :tb303 :in-cutoff) (fn [val] (t/set-dataArray-item 1 val )) ::tb303 )

(def flm (control-bus-monitor (get-ctrl-bus :flute)))

@flm

(remove-event-handler ::tb303)
                                        ;indices 0-50 for control, indiced 51-100 for video selection


(add-watch flm :trg (fn [_ _ old new]
                      (let [])
                                        (t/set-dataArray-item 2 (+ 1 new) )

                      ;(println "asdas" new)
                                        ;(t/set-dataArray-item 1 (+ (nth (control-bus-get kickttrig5) 0) 0.01) )
                                        ;(t/set-dataArray-item 2 (+ (nth (control-bus-get ldf) 0) 0.01) )
                                        ;(t/set-dataArray-item 0 (+ (nth (control-bus-get vcbus1) 0) 0.01) )
                                        ;(t/set-dataArray-item 0 (+ (nth (control-bus-get vcbus1) 0) 0.01) )



                     )
           )


(remove-watch ldm :trg)
(control-bus-get kickttrig)

@tbmnt9

(t/set-dataArray-item 2 10)

q(do
                                        ;sepede 51000, 51700
  (t/bufferSection 1 0 51700)

qq
  (t/set-video-fixed 1 :fw)

  (t/set-video-fps 2 10)

  )

(t/toggle-recording "/dev/video1")

(t/stop)

(stop)
