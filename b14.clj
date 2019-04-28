(ns b14 (:use [trigger.trigger] [trigger.synths] [overtone.core]) (:require [viritystone.tone :as t]) )



(-> {:pn "snare" :sn snare :in-trg ["[1 2 3 4 5 6 8 9 10 11 12 13 14 15 16]" "[-]" "[-]" "[-]"   "[[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]" "[-]" "[-]" "[-]"] :in-amp ["[2]"] } trg)

(-> {:pn "kick" :sn kick :in-trg ["[1 0.001]" "[-]" "[ 2 - 2 -]"] :in-f2 ["[80 60]"] :in-amp ["[0.01]"] } trg)

(-> {:pn "tb303" :sn tb303 :in-trg ["[1  [1 [1 [1 [1 1]]]]]" "[1 1 1 1 1 1 1 [1 1 1 1]]" "[1 1 1 1 1 1 1 1]" "[1 1 1 1]"] :in-amp ["[0.58]"] :in-note ["[22]" "[20]" "[20]"]  :in-gate-select ["[1]" "[0]"] :in-attack ["[0.0001]"] :in-decay ["[0.9]"] :in-sustain ["[0.5]"] :in-release ["[0.3]"] :in-r ["[0.9]"] :in-cutoff ["[2500 2500]" "[2000 1000]" "[1000 900 800 700 600 500 400 300 200 100]"] :in-wave ["[1]"]} trg )

(-> {:pn "super"  :sn  supersaw :in-freq ["[50 51 52 53 54 55 56 57 58 ]"] :in-amp ["[0.123]"]} trg)

(stp "tb303")

(stp "kick")

(stp  "snare")

(stp "super")

(stp "flute")

(stp "lead")

(stp "lead2")

(-> {:pn "kick" :sn kick :in-trg ["[1  [2 [3 4 5 [6 [7 8 9  10]]]]]" "[1 2]"  "[1 2]"  "[1 2]" "[1 2 3 4]" "[1 2]" "[1 2]" "[1 2]"  ] :in-f1 ["[100 200 300 400 500]" "[100 200]"] :in-amp ["[0.03]"]:in-f2 ["[80 60]"] } trg)


(-> {:pn "kick" :sn kick :in-trg ["[1  [2 [3 4 5 [6 [7 8 9 10]]]]]" "[10 11 12 13 41 15 16 17 18 1 1 1 1 1 1 1  1 1 1 1]" ] :in-f1 ["[100 200 300 400 500]"] :in-amp ["[0.02]"] } trg)


(-> {:pn "snare" :sn snare :in-trg ["[1 1 5 - 1]"] :in-amp ["[1]"] } trg)

(-> {:pn "mooger" :sn mooger :in-trg ["[1]"] :in-amp ["[1]"] :in-note ["[60]" "[59]" "[61]" "[62 61 59 58]" "[60 [59 62]]" "[61]" "[62]" "[61]" ]} trg)

(stp "mooger")


(-> {:pn "mooger2"
     :sn mooger
     :in-trg ["[1 1 1 1]" "[1]" "[1]"]
     :in-amp ["[3]"]
     :in-note ["[30 31 32 30]" "[30 40 35 40]" "[30 40 30 40]" "[40]" "[30]"]
     :in-gate-select ["[1]"]
     :in-osc1 ["[1 1 0 0]"]
     :in-osc2 ["[2 2 1 1]"]
     :in-attack ["[0.0021]"]
     :in-decay ["[0.95]"]
     :in-sustain ["[0.4]"]
     :in-release ["[0.03]"]
     :in-cutoff  ["[1000 800 600 400 300 200 100 200 300 400 500 600 700 800 900]"]
     :in-fattack ["[0.022]"]
     :in-fdecay ["[0.9]"]
     :in-fsustain ["[0.9]"]
     :in-frelease ["[0.01]"]
     :in-osc2-level ["[2]"]
     :in-osc1-level ["[1]"]} trg)




(-> {:pn "tb303" :sn tb303 :in-trg ["[1  [1 [1 [1 [1 [1 1]]]]]]" "[1 1 1 1 1 1 1 [1 1 1 1]]" "[1 1 1 1 1 1 1 1]" "[1 1 1 1]" "[1 1 1 1]"] :in-amp ["[1]"] :in-note ["[22]" "[20]" "[20]"]  :in-gate-select ["[1]" "[0]"] :in-attack ["[0.0001]"] :in-decay ["[0.9]"] :in-sustain ["[0.5]"] :in-release ["[0.3]"] :in-r ["[0.9]"] :in-cutoff ["[2500 2500]" "[2000 1000]" "[1000 900 800 700 600 500 400 300 200 100]"] :in-wave ["[1]"]} trg )


(-> {:pn "tb3032" :sn tb303 :in-trg ["[1  [1 [1 [1 [1 1]]]]]" "[[[[[1 1] 1] 1] 1] 1]" "[1 1 1 1]"] :in-amp ["[1]"] :in-note ["[20]" "[21]"]  :in-gate-select ["[1]"] :in-attack ["[0.0001]"] :in-decay ["[0.9]"] :in-sustain ["[0.5]"] :in-release ["[0.23]"] :in-r ["[0.19]"] :in-cutoff ["[500 20000]" "[20000 1000]"] :in-wave ["[1]"]} trg )



(-> {:pn "kick" :sn kick :in-trg ["[- 1]" "[-]" "[1 1]" "[-]" "[1 [1 1 1 1]]"  "[-]"  "[1 1 [1 1] -]" ] :in-f3 ["[100]"] :in-amp ["[0.0001]"]:in-f2 ["[80 60]"] } trg)


(-> {:pn "kick" :sn kick :in-trg ["[1  [2 [3 4 5 [6 [7 8 9  10]]]]]" "[1 2]"  "[1 2]"  "[1 2]" "[1 2 3 4]" "[1 2]" "[1 2]" "[1 2]"  ] :in-f1 ["[100 200 300 400 500]" "[100 200]"] :in-amp ["[0.03]"]:in-f2 ["[80 60]"] } trg)


(-> {:pn "kick3" :sn kick :in-trg ["[1  [2 [3 4 5 [6 [7 8 9 10]]]]]" "[10 11 12 13 41 15 16 17 18 1 1 1 1 1 1 1  1 1 1 1]" ] :in-f1 ["[90]"]
     :in-amp ["[0.02]"]} trg)

;kick amp 0.02

(-> {:pn "flute"
     :sn simple-flute
     :in-trg ["[1 1 1 1 ]"]
     :in-freq ["[880]" "[880]" "[880]" "[880]" "[880]" "[880]" "[880]" "[880]" "[880]" "[800]" "[800]" "[800]" "[800]" "[800]" "[800]" "[800]" "[800]"]
     :in-gate-select [ "[0]"  "[0]"  "[0]"  "[0]"  "[1]"  "[1]"  "[1]"  "[1]" ]
     :in-attack ["[0.0001]"]
     :in-decay  ["[0.91]"]
     :in-sustain ["[0.75]"]
     :in-release [ "[0.9]"]
     :in-amp ["[0.27]"]
     :in-ctrl-select ["[2]"]} trg)



(-> {:pn "flute2"
     :sn simple-flute
     :in-trg ["[1 1 1 1]"]
     :in-gate-select ["[0]"]
     :in-freq ["[588]""[588]" "[588]" "[588]" "[588]" "[588]" "[588]" "[588]" "[1880]" "[1800]" "[1800]" "[1800]" "[1800]" "[1800]" "[1800]" "[1800]" "[1800]"]
     :in-amp ["[0.9]"]} trg)


(-> {:pn "lead"
     :sn cs80lead
     :in-trg ["[1]"]
     :in-freq ["[1880]""[1880]" "[1880]"  "[1880]" "[880]" "[880]" "[880]" "[880]" "[880]" "[880]" "[800]" "[800]" "[800]" "[800]" "[800]" "[800]" "[800]" "[800]" "[800]" "[800]"]
     :in-vibrate ["[5]" "[6]" "[7]" "[8]" "[10]" "[14]" "[20]" "[25]" "[30]" "[14]" "[12]" "[10]" "[8]" "[5]" "[2]" "[1]" "[0]"]
     :in-amp ["[0.19]"]}trg)


(-> {:pn "lead2"
     :sn cs80lead
     :in-trg ["[1]"]
     :in-freq ["[440]""[440]" "[440]" "[440]" "[440]" "[440 880 440 880 440 880 440 880 440 880 440 880 440 880 440 880 440 880 440 880 440 880 440 880 440 880 440 880]" ]
     :in-vibrate ["[15]" "[16]" "[17]" "[18]" "[19]" "[18]" "[17]" "[16]" "[15]" "[14]" "[10]" "[4]" "[0]" "[0]" "[0]" "[0]" "[0]"  "[19]" "[18]" "[17]" "[16]" "[15]" "[14]"]
     :in-dtune  ["[0.2]" "[0.1]" "[0.01]"]
     :in-amp ["[0.9]"]}trg)

(stp "lead2")
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
