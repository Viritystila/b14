(ns b14 (:use [trigger.trigger] [trigger.synths] [overtone.core]) (:require [viritystone.tone :as t]) )

(require 'markov-chains.core)


(def first-order-prob-matrix {
  [:A4]  { :A4 0.1  :C#4 0.06  :Eb4 0.3 }
  [:C#4] { :A4 0.925 :C#4 0.05 :Eb4 0.07 }
  [:Eb4] { :A4 0.7  :C#4 0.03  :Eb4 0.9 }})


(def second-order-prob-matrix {
  [:A2 :A2] { :A2 0.18 :D2 0.6  :G2 0.22 }
  [:A2 :D2] { :A2 0.5  :D2 0.5  :G2 0    }
  [:A2 :G2] { :A2 0.15 :D2 0.75 :G2 0.1  }
  [:D2 :D2] { :A2 0    :D2 0    :G2 1    }
  [:D2 :A2] { :A2 0.25 :D2 0    :G2 0.75 }
  [:D2 :G2] { :A2 0.9  :D2 0.1  :G2 0    }
  [:G2 :G2] { :A2 0.4  :D2 0.4  :G2 0.2  }
  [:G2 :A2] { :A2 0.5  :D2 0.25 :G2 0.25 }
  [:G2 :D2] { :A2 0.21  :D2 0    :G2 0    }})



(lss)

(trg :snare snare
     :in-trg   [[(repeat 8 1)] r] [[1 1] r] [[1 1] 1] [r]
     :in-amp [0.2])


(stp :snare)

(trg :kick
      kick
      :in-trg  [[1 1] r] [[1 1] r] [[1 1] 1] [r] ; [1 1 1 [(repeat 8 1)]] [[1 1] 1 1 [1 1 1 1] ]
     :in-f2 [(map midi->hz (map note (take 4 (markov-chains.core/generate first-order-prob-matrix))))] :in-amp [0.001 0.004])


(stp :kick)

(trg :kick2 trigger.synths/kick2 :in-trg  (repeat 3 [r])  (shuffle (vec (flatten (take 4 (repeat 64 [1 r])))))  (repeat 4 [(repeat 8 1)]) :in-amp [0.4])


(stp :kick2)


(stp :snare3)

(stop)

(ctl base-trigger :del 0.9)

(trg :kick
     kick
     :in-trg
     [1 r [1 1] r]
     [r 1 r 1]
     [1 1 1 1]
     [r [1 1] r [1 1 1 1]]
     :in-f1 [190]
     :in-f2 [80 60]
     :in-f3 [90] [100] [110] [110]
     :in-amp [0.01])


(trg :kick2  trigger.synths/kick2 :in-trg (shuffle (range 0 200 1)) [(range 200 0 -50)] [10 20 30 [50 60] 10 20 30 10] :in-amp [0.9])

(trg :tb303 tb303 :in-trg [1 1 1 1] [(repeat 8 1)] :in-amp [0.4] :in-note [(note :c1)] [(note :a1)] [(note :d1)]  :in-gate-select [1 0] [ 0 1] :in-attack [0.001] :in-decay [0.9] :in-sustain [0.5] :in-release [0.3] :in-r [0.9] :in-cutoff [2500 2000] [2000] :in-wave [1])


(trg :super supersaw :in-freq [(range 50 66 1)] [66] [66] [(range 66 50 -1)] [50] [50] :in-amp [0.05])

(stp :tb303)

(stp :kick)

(stp  :snare)

(stp :super)

(stp :flute)

(stp :flute2)

(stp :lead)

(stp :lead2)

(stp :super)

(trg :kick kick :in-trg [1 [2 [3 4 5 [6 [7 8]]]]] (repeat 3 [1 2]) [1 2 3 4]  (repeat 3 [1 2])  :in-f1 [(range 100 500 100)] [100 200] :in-amp [0.03]:in-f2 [80 60] )

(trg :kick kick :in-trg [1 [2 [3 4 5 [6 [7 8]]]]]  [(range 10 16 1) (repeat 8 1)] :in-f1 [300] :in-f2 [100] :in-f3 [100]  :in-amp [0.02] )

(trg :kick
     kick
     :in-trg [(repeat 8 1)] [(repeat 3 r) [1 3] (repeat 3 r) ]
     :in-f1 [(range 100 180 10)] [(range 180 100 -10)]
     :in-f2 [100]
     :in-f3 [90 90 90 200 220 100 100 100]
     :in-amp [0.02] )


(trg :snare snare :in-trg [1 1 5 r 1] :in-amp [1] )

(stp :snare)

(trg :mooger
    mooger
    :in-trg [1 1 1 1] [1] [1]
    :in-amp [0.3]
    :in-note  (shuffle (chord :d2 :minor))
    :in-gate-select [1]
    :in-osc1 [1 1 0 0]
    :in-osc2 [2 2 1 1]
    :in-attack [0.0021]
    :in-decay [0.95]
    :in-sustain [0.4]
    :in-release [0.03]
    :in-cutoff   (map vec (partition 4 (flatten [(range 1000 200 -200) (range 100 900 100)])))
    :in-fattack [0.022]
    :in-fdecay [0.9]
    :in-fsustain [0.9]
    :in-frelease [0.01]
    :in-osc2-level [2]
    :in-osc1-level [1])

(stp :mooger)


(lss)

(trg :tb303
    tb303
    :in-trg (repeat 4 [1 1 1 1])
    :in-amp [0.2]
    :in-note (map (fn [x] [x] ) (map note (take 12 (markov-chains.core/generate second-order-prob-matrix))))
    :in-gate-select [0]
    :in-attack [0.001]
    :in-decay [0.9]
    :in-sustain [0.5]
    :in-release [0.3]
    :in-r [0.09]
    :in-cutoff [200]       ;[2500 2500] [2000 1000] [(range 1000 100 -100)]
    :in-wave [1])


(stp :tb303)

(trg :kick
    kick
    :in-trg [r 1] [r] [1 1] [r] [1 [1 1 1 1 ]] [r] [1 1 [1 1] r]
    :in-f3 [100]
    :in-amp [0.01]
    :in-f2 [80])


(trg :flute
    simple-flute
    :in-trg [1 1 1 1]
    :in-freq (repeat 8 [440]) (repeat 8 [800])
    :in-gate-select (repeat 4 [0]) (repeat 4 [0])
    :in-attack [0.01]
    :in-decay  [0.91]
    :in-sustain [0.75]
    :in-release [ 0.9]
    :in-amp [0.3]
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
    :in-freq (repeat 8 [(midi->hz (note :e6))]) (repeat 8 [(midi->hz (note :c6))])
    :in-vibrate [5] [6] [7] [8] [10] [12] [14] [20] [25] [30] [14] [12] [10] [8] [5] [2] [1] [0]
    :in-amp [0.15])


(trg :lead2
    cs80lead
    :in-trg [1]
    :in-freq (repeat 5 [440]) [(take 14 (cycle [440 880]))]
    :in-vibrate [15] [16] [17] [18] [19] [18] [17] [16] [15] [14] [10] [4] (repeat 5 [0]) [19] [18] [17] [16] [15] [14]
    :in-dtune [0.2] [0.1] [0.01]
    :in-amp [0.39])

(stp :lead)


(trg :bow
     bowed
     :in-trg (repeat 2  [r]) (vec (repeat 1 (seq [1 1 1 [1 [1 1]] ]))) [[1 1] 1 1 1]
     :in-amp [1] [1] [1] [1]
     :in-note [(chord :d2 :major)] (shuffle (chord :g2 :minor))
     :in-velocity [1]
     :in-gate-select [1] [1] [1] [1 1]
     :in-bow-offset [0.01]
     :in-bow-position [1.75]
     :in-bow-slope [0.08]
     :in-vib-freq [0.127]
     :in-vib-gain [0.19] )

(stp :bow)

(shuffle (chord :e3 :major))

(trg :ks1
     ks1
     :in-trg (repeat 3 [r]) [(repeat 32 1)]
     :in-dur [3]
     :in-amp [0.1]
     :in-note (repeat 3 [(chord-degree :i :d4 :melodic-minor)])  [(chord-degree :i :d4 :melodic-major)]  (repeat 3 [(chord-degree :i :d4 :melodic-minor)])  [(shuffle (chord-degree :i :d4 :melodic-minor))]
     :in-decay [0.1]
     :in-coef [(range 0.01 0.9 0.01)]  )


(stp :ks1)

(trg :vb
     vintage-bass
     :in-trg [r 1] [r 1] [r] [1 1 1 [1 1 1 1]]
     :in-gate-select [0] [0] [0] [1]
     :in-amp [0.5]
     :in-note  [(note :d2)]  [(note :d2)]
     :in-a [0.001]
     :in-d [0.3]
     :in-s [0.7]
     :in-r [0.8])

(stp :vb)

(trg :tom1
     tom
     :in-trg (map vec (partition 1 [1 1 1 [1 1 1 [1 1 1 [1 1 1 1]]]])) [r] [r] [[1 1 1 1] 1 1 1]
     :in-stick-level [0.5]
     :in-amp [0.3])

(lss)

(chord-degree :i :d4 :ionian)

(scale :c4 :minor)

(stp :tom1)

(odoc crackle)

(stop)
                                        ;Video
(t/start "./b14.glsl" :width 1920 :height 1080 :cams [0 1] :videos ["../videos/tietoisku_1_fixed.mp4" "../videos/spede_fixed.mp4"  "../videos/vt2.mp4" "../videos/hapsiainen_fixed.mp4" "../videos/sormileikit.mp4"])


                                        ;Video
(t/start "./b14.glsl" :width 1920 :height 1080 :cams [0 1] :videos ["../videos/soviet1.mp4" "../videos/uni_fixed.mp4" "../videos/soviet2.mp4"])


(t/post-start-video "../videos/spede_fixed.mp4" 3)

(t/post-start-video "../videos/soviet4.mp4" 4)


(lss)

(stop)



;;;Algo tests
;;;;;;;;;(vec (repeat 4 (seq [1 2 3 4])))
;;;;;;;;(map vec (partition 2 [1 2 3 4]))


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

(def timeAtom (atom 0))

(on-trigger 0 (fn [val] (println (- (System/currentTimeMillis) @timeAtom)) (reset! timeAtom (System/currentTimeMillis)) ) ::kick )

(remove-event-handler ::kick
 )

(on-trigger (get-trigger-val-id :kick :in-trg) (fn [val] (t/set-dataArray-item 0 val)(t/set-fixed-buffer-index 1 :inc) ) ::kick )

(on-trigger (get-trigger-val-id :snare :in-trg) (fn [val] (t/set-dataArray-item 1 val) (t/set-fixed-buffer-index 3 :dec)) ::snare )

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

(t/set-video-frame 0 4000) ;Soviet1, bluscreen1

(t/set-video-frame 0 16800) ;Soviet1, nuket

(t/set-video-frame 1 6460) ; Uni, pekkahäkki


(do
                                        ;spede 51000, 51700
  (t/bufferSection 0 0 4000)

  (t/bufferSection 1 0 6460)

  (t/bufferSection 2 0 29460)

  (t/bufferSection 3 0 51000)

  (t/bufferSection 4 0 0)


  (t/set-video-fixed 0 :fw)


  (t/set-video-fixed 1 :fw)

  (t/set-video-fixed 2 :fw)

  (t/set-video-fixed 3 :fw)

  (t/set-video-fixed 4 :fw)



  (t/set-video-fps 2 10)

  )
(t/set-fixed-buffer-index 1 :ff 100)

(t/toggle-recording "/dev/video1")

(t/stop)

(stop)
