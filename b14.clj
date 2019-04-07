(ns b14 (:use [overtone.live]) (:require [viritystone.tone :as t]) )

;(defsynth aaa []  (out 0 (sin-osc 100)))

;(def aa (aaa))

;(kill aa)

(do
  (do
    (do (defonce pointLength 11))
    (defn note->hz [music-note]
      (midi->hz (note music-note)))

    (defn writeBuffer [buffer value-array] (let [va  (into [] (flatten value-array))]
                                             (buffer-write! buffer va) va))

    (defn setChords [value-array note chordval point] (let [values          11
                                                            chrd            (chord note chordval)
                                                            freqs           (vec (map  note->hz chrd))
                                                            maxChordLength  (min (count freqs) 4)
                                                            freqs           (into [] (subvec freqs 0 maxChordLength))
                                                            base-indices    (range (count freqs))
                                                            base-indices    (into [] (map + base-indices (repeat maxChordLength (+ 1 (* values point)))))]
                                                        (apply assoc value-array (interleave base-indices  freqs ))))

     (defn setADSR [value-array a d s r point] (let [values          11
                                                     adsr            [a d s r]
                                                     base-indices    (range 4)
                                                     base-indices    (map + base-indices (repeat 4 (+ 5 (* values point))))]
                                                 (apply assoc value-array (interleave base-indices  adsr ))))


     (defn setAmp [value-array amp point] (let [values          11 ]
                                            (assoc value-array (+ 9 (* values point)) amp)))


     (defn setAmp [value-array amp point] (let [values          11 ]
                                            (assoc value-array (+ 9 (* values point)) amp)))


     (defn makeBuffer [points ] (let [values      11
                                      buff        (buffer (* points values))
                                      value-array (into [] (repeat (* points values) 0 ))]
                                  (buffer-write! buff value-array)
                                  value-array))

     (defn makeBarArray [] (let [values      11
                                  points      264
                                  p1h         (/ 265 2)
                                  value-array (into [] (repeat (* points values) 0 )) ]
                               value-array))


    )

    (do
    (defonce master-rate-bus (control-bus))
    (defonce root-trg-bus (control-bus)) ;; global metronome pulse
    (defonce root-cnt-bus (control-bus)) ;; global metronome count

    (defonce b1st_beat-trg-bus (control-bus)) ;; beat pulse (fraction of root)
    (defonce b1st_beat-cnt-bus (control-bus)) ;; beat count

    (defonce b4th_beat-trg-bus (control-bus)) ;; beat pulse (fraction of root)
    (defonce b4th_beat-cnt-bus (control-bus)) ;; beat count

    (defonce b8th_beat-trg-bus (control-bus)) ;; beat pulse (fraction of root)
    (defonce b8th_beat-cnt-bus (control-bus)) ;; beat count

    (defonce b16th_beat-trg-bus (control-bus)) ;; beat pulse (fraction of root)
    (defonce b16th_beat-cnt-bus (control-bus)) ;; beat count

    (defonce b32th_beat-trg-bus (control-bus)) ;; beat pulse (fraction of root)
    (defonce b32th_beat-cnt-bus (control-bus)) ;; beat count

    (def FRACTION_1 "Number of global pulses per beat" 1)
    (def FRACTION_4 "Number of global pulses per beat" 4)
    (def FRACTION_8 "Number of global pulses per beat" 8)
    (def FRACTION_16 "Number of global pulses per beat" 16)
    (def FRACTION_32 "Number of global pulses per beat" 32)

    )

  (do
    (defsynth root-trg [rate 100]
      (out:kr root-trg-bus (impulse:kr (in:kr rate))))

    (defsynth root-cnt []
      (out:kr root-cnt-bus (pulse-count:kr (in:kr root-trg-bus))))

    (defsynth b1st_beat-trg [div FRACTION_1]
      (out:kr b1st_beat-trg-bus (pulse-divider (in:kr root-trg-bus) div)))

    (defsynth b1st_beat-cnt []
      (out:kr b1st_beat-cnt-bus (pulse-count (in:kr b1st_beat-trg-bus))))

    (defsynth b4th_beat-trg [div FRACTION_4]
      (out:kr b4th_beat-trg-bus (pulse-divider (in:kr root-trg-bus) div)))

    (defsynth b4th_beat-cnt []
      (out:kr b4th_beat-cnt-bus (pulse-count (in:kr b4th_beat-trg-bus))))

    (defsynth b8th_beat-trg [div FRACTION_8]
      (out:kr b8th_beat-trg-bus (pulse-divider (in:kr root-trg-bus) div)))

    (defsynth b8th_beat-cnt []
      (out:kr b8th_beat-cnt-bus (pulse-count (in:kr b8th_beat-trg-bus))))

    (defsynth b16th_beat-trg [div FRACTION_16]
      (out:kr b16th_beat-trg-bus (pulse-divider (in:kr root-trg-bus) div)))

    (defsynth b16th_beat-cnt []
      (out:kr b16th_beat-cnt-bus (pulse-count (in:kr b16th_beat-trg-bus))))

    (defsynth b32th_beat-trg [div FRACTION_32]
      (out:kr b32th_beat-trg-bus (pulse-divider (in:kr root-trg-bus) div)))

    (defsynth b32th_beat-cnt []
      (out:kr b32th_beat-cnt-bus (pulse-count (in:kr b32th_beat-trg-bus))))
    )

  (do
    (def r-trg (root-trg master-rate-bus))
    (def r-cnt (root-cnt [:after r-trg]))
    (def b1st-trg (b1st_beat-trg [:after r-trg]))
    (def b1st-cnt (b1st_beat-cnt [:after b1st-trg]))
    (def b4th-trg (b4th_beat-trg [:after r-trg]))
    (def b4th-cnt (b4th_beat-cnt [:after b4th-trg]))
    (def b8th-trg (b8th_beat-trg [:after r-trg]))
    (def b8th-cnt (b8th_beat-cnt [:after b8th-trg]))
    (def b16th-trg (b16th_beat-trg [:after r-trg]))
    (def b16th-cnt (b16th_beat-cnt [:after b16th-trg]))
    (def b32th-trg (b32th_beat-trg [:after r-trg]))
    (def b32th-cnt (b32th_beat-cnt [:after b32th-trg]))
    (control-bus-set! master-rate-bus (* 2 36))
    )
  (do
    (defonce main-g (group "main bus"))
    (defonce early-g (group "early bus" :head main-g))
    (defonce later-g (group "late bus" :after early-g)))


  (do
    (defonce mcbus1 (control-bus 11))
    (defonce mcbus2 (control-bus 11))
    (defonce mcbus3 (control-bus 11))
    )

  (do
    (defonce buffer-64-1 (buffer 64))
    (defonce buffer-64-2 (buffer 64 11))
    )

  (do (defonce cbus1 (control-bus 1)))

  (do (defonce vcbus1 (control-bus 1))
      (defonce vcbus2 (control-bus 1))
      (defonce vcbus3 (control-bus 1))
      (defonce vcbus4 (control-bus 1))
      )


  (do (defonce pointLength 11))
  )



(odoc stepper:kr)

(odoc t-delay)

(odoc toggle-ff)

(odoc gate)

(odoc trig1)

(odoc send-trig)

(odoc cycle-fn)

(odoc set-reset-ff)

(odoc scaled-play-buf)

(odoc buffer-write!)
(do
  (def bpm 120)
  (def bps (/ bpm 60))

  (def phrases 1)
  (def bars-per-phrase 1)
  (def beats-per-bar 4.0)
  (def bar-duration (/ beats-per-bar bps))
  (def beat-duration (/ bar-duration beats-per-bar))
  (def half-beat-duration (/ beat-duration 2))
  (def quarter-beat-duration (/ beat-duration 4))
  (def eight-beat-duration (/ beat-duration 8))
  (def sixteenth-beat-duration (/ beat-duration 16))
  (def thirtysecond-beat-duration (/ beat-duration 32))
  (def beat-rate (/ 1 thirtysecond-beat-duration))

  (def ticks-per-bar (* beats-per-bar 32))






  (def barArray (makeBarArray))

  (defn setBeat [bar-array ])


  (defonce rcnt-b (control-bus)) ;; global metronome count


  (defsynth rnct [rate 0 out-bus 0 beats-per-bar 4 bpm 10] (let [bps   (/ bpm 60)
                                                                 pulse (impulse:kr bps)
                                        ;trg1   (trg1)
                                                                 stp2  (stepper:kr pulse)]  (out:kr out-bus (mod (pulse-count:kr pulse) beats-per-bar ))))

  (def rnct-s (rnct (* 2 36) rcnt-b 4)))

(control-bus-get rcnt-b)

(odoc pulse-divider)


(defn makeBar ([] (let [whole-note 1
                        half-note (/ whole-note 2)
                        quarter-note (/ whole-note 4)
                        eight-note (/ whole-note  8)
                        sixteenth-note (/ whole-note 16)
                        thirtysecond-note (/ whole-note 32)
                        sixtyfourth-note  (/ whole-note 64)
                        value-array (into [] (repeat (int ticks-per-bar) 0))]
                        value-array  )))

(def cb1 (control-bus))

(def cb2 (control-bus))


(defsynth tst [trig-in 0 counter-in 0 dur-buffer-in 0 out-bus 0 cntrl-bus 0 b 0] (let [trg-in       (in:kr trig-in)
                                                                                        cntr-in      (in:kr counter-in)
                                        ;trg (demand:kr (in:kr trg-in) 0 (dbufrd dur-buffer-in cntr-in) )
                                                                                        trg (t-duty:kr (dbufrd dur-buffer-in (dseries 0 1 INF) 1))
                                                                                        env (env-gen (perc 0.01 0.01 1 0) :gate trg)
                                                                                        ] (out:kr out-bus trg)
                                                                                  (out 0 (* 1 env (sin-osc (* 1 100  ))))))


(def bub (buffer 8))

(defn set-buffer [buf new-buf-data] (let [size (count (vec (buffer-data buf)))
                                        clear-buf-data (into [] (repeat size 0))]
                                      (buffer-write! buf clear-buf-data)
                                      (buffer-write! buf new-buf-data)
                                        ))


(defn triggerDur [dur] (if (= dur 0) 0 1) )



(defn traverseVector ([input-array] (let [input-vec input-array
                                           ;_ (println input-vec)
                                           result []]
                                          (if true ;(vector? input-vec)
                                            (loop [xv (seq input-vec)
                                                   result []]
                                              (if xv
                                                (let [_ (println xv)
                                                      length (count input-vec)
                                                      x (first xv)]
                                                  (if (vector? x) (recur (next xv) (conj result (traverseVector x length)))
                                                      (recur (next xv) (conj result (/ (triggerDur x) length 1))))) result)))))
  ([input-array bl] (let [input-vec input-array
                          _ (println bl)
                          ]
                                          (if (vector? input-vec)
                                            (loop [xv (seq input-vec)
                                                   result []]
                                              (if xv
                                                (let [length (count input-vec)
                                                      x (first xv)]
                                                  (if (vector? x) (recur (next xv) (conj result (traverseVector x (* bl length))))
                                                      (recur (next xv) (conj result (/ (triggerDur x) length bl))))) result)))))
)

(defn countZeros [input-vector] (loop [xv (seq input-vector)
                                       sum 1]
                                  (if xv
                                    (let [x (first xv)]
                                      ;(println x)
                                      (if (= x 0) (do (println x) (recur (next xv) (+ 1 sum))) sum)) sum)))

(countZeros [0 0 1 0])


(defn adjustDuration [input-vector] (let [length   (count input-vector)
                                          idxs (range length)]
                                      (loop [xv (seq idxs)
                                             result []]
                                        (if xv
                                          (let [xidx    (first xv)
                                                nidx    (mod (+ 1 xidx) length)
                                                opnext  (nth input-vector nidx)
                                                op      (nth input-vector xidx)
                                                _  (println (subvec input-vector nidx))
                                                ;_ (println (countZeros (subvec input-vector nidx)))
                                                op      (if (= 0 opnext) (* op (countZeros (subvec input-vector nidx))) op)]
                                                (recur (next xv) (conj result op))) result))))

(adjustDuration [1 0 0 0 1 0])

(defn generateDurations [input] (let [durs  (traverseVector input)
                                      durs  (into [] (flatten durs))
                                      durs  (adjustDuration durs)]durs) )

(traverseVector [1 2 0 4])

(doseq [x [ 1 2 3]] (println x))

(range 10)
(set-buffer bub (generateDurations [1 0 1 0 1 0 0 1]))


(subvec [1 2 3] (mod 3 3))

(def ttt (tst b8th_beat-trg-bus b8th_beat-cnt-bus bub cb1 0))

(ctl ttt :b 0)

(control-bus-get cb1)

(control-bus-set! cb2 3)

(pp-node-tree)


(kill ttt)

(kill 148)


(subvec [1 2 3] 4)

                                        ;single bar buffer, 4 beats, 8 senconds, 12 thirds, 16 fourths, 32 eights, 64 16ths, 128 32nds, 264 64ths
                                        ;[0 1 2 3]
                                        ;[[0 1 2 3] [0 1 2 3] [0 1 2 3] [0 1 2 3]]
                                        ;[0                   11                          22
                                        ;  1        6           12           17             23
                                        ;   2 3 4 5   7 8 9 20    13 14 15 16  18 19 20 21    24 25 26 27
                                        ;
(def asas [[0 1 0 1] 0 [0 1 [0 1 1 1] 0] 1])

(def base)

asas
(count asas)
                                        ;buffers

(loop [x asas] (println x))


(buffer-write! buffer-64-1 [ 4 4 4 4 4 4 4 4
                             4 4 4 4 4 4 4 4
                             4 4 4 4 4 4 4 4
                             3 3 3 3 3 3 3 3
                             2 2 2 2 2 2 2 2
                             2 2 2 2 2 2 2 2
                             2 2 2 2 2 2 2 2
                             2 2 2 2 2 2 2 2])


                                        ; single pulse point
; p=[gate, f1, f2, f3, f4, a, d, s, r, amp, dur]
                                        ;Collection of points
(do
  (def pointBuffer (buffer (* 5 pointLength)))
  (def modValArray (writeBuffer pointBuffer (flatten [0 0 0 0 0 0 0 0 0 0
                                                      1 (into [] (map note->hz (chord :C3 :minor))) 1 0.01 0.003 0.99 10.1 1
                                                      1 (into [] (map note->hz (chord :D3 :minor))) 1 0.01 0.003 0.99 10.1 1
                                                      1 (into [] (map note->hz (chord :G3 :minor))) 1 0.01 0.003 0.99 10.1 1
                                                      1 (into [] (map note->hz (chord :Bb3 :minor))) 1 0.01 0.003 0.99 10.1 1])))

  )

(do
  (def pointBuffer_major (buffer (* 5 pointLength)))
  (def modValArray (writeBuffer pointBuffer_major (flatten [0 0 0 0 0 0 0 0 0 0
                                                      1 (into [] (map note->hz (chord :C3 :major))) 1 0.01 0.003 0.99 0.01 1
                                                      1 (into [] (map note->hz (chord :D3 :major))) 1 0.01 0.003 0.99 0.01 1
                                                      1 (into [] (map note->hz (chord :G3 :major))) 1 0.01 0.003 0.99 0.01 1
                                                      1 (into [] (map note->hz (chord :Bb3 :major))) 1 0.01 0.003 0.99 0.01 1])))

  )


(def playBuffer (buffer 128))
(buffer-write! playBuffer [1 1 1 0 0 0 0 0
                           4 0 0 0 0 0 0 0
                           4 0 0 0 0 0 0 0
                           4 0 0 0 0 0 0 0
                           4 0 0 0 0 0 0 0
                           4 0 0 0 0 0 0 0
                           3 0 0 0 0 0 0 0
                           3 0 0 0 0 0 0 0
                           2 0 0 0 0 0 0 0
                           2 0 0 0 0 0 0 0
                           2 0 0 0 0 0 0 0
                           2 0 0 0 0 0 0 0
                           2 0 0 0 0 0 0 0
                           2 0 0 0 0 0 0 0
                           2 0 0 0 0 0 0 0
                           4 0 0 0 0 0 0 0])

(buffer-write! playBuffer barb)

(def bbb (buffer 8))
(buffer-write! bbb [1 0 0 0 0 0 0 1])

                                        ;buffer modifiers

(def modValArray (writeBuffer pointBuffer  (setChords modValArray :E#2 :minor 1)))

(def modValArray (writeBuffer pointBuffer (setADSR modValArray 0.01 0.3 0.99 0.1 4)))

(def modValArray (writeBuffer pointBuffer (setAmp modValArray 1 3)))

(def modValArray (writeBuffer pointBuffer (setPoint modValArray [1 200 334 455 576 0.01 0.3 0.99 0.01 1] 3)))

                                        ;readers

(defsynth playReader [play-buf 0
                      point-buf 0
                      in-bus-ctr 0
                      outbus 0 ]
    (let [ctr-in    (in:kr in-bus-ctr)
          point     (buf-rd:kr 1 play-buf ctr-in)
          values    10
          maxp      5
          gate      (buf-rd:kr 1 point-buf (+ 0 (* values point)))
          f1        (buf-rd:kr 1 point-buf (+ 1 (* values point)))
          f2        (buf-rd:kr 1 point-buf (+ 2 (* values point)))
          f3        (buf-rd:kr 1 point-buf (+ 3 (* values point)))
          f4        (buf-rd:kr 1 point-buf (+ 4 (* values point)))
          a         (buf-rd:kr 1 point-buf (+ 5 (* values point)))
          d         (buf-rd:kr 1 point-buf (+ 6 (* values point)))
          s         (buf-rd:kr 1 point-buf (+ 7 (* values point)))
          r         (buf-rd:kr 1 point-buf (+ 8 (* values point)))
          amp       (buf-rd:kr 1 point-buf (+ 9 (* values point)))
          ]
      (out:kr outbus [gate f1 f2 f3 f4 a d s r amp])))

                                         ;Reader for kick drum

(def kickr (playReader :play-buf bbb :point-buf pointBuffer :in-bus-ctr b8th_beat-cnt-bus :outbus mcbus3))

(ctl kickr :in-bus-ctr b8th_beat-cnt-bus :play-buf bbb)


(control-bus-get mcbus3)

(kill kickr)

(pp-node-tree)
                                        ;Kick
(defsynth kick [freq 80
                amp 1
                amp_output 1
                v1 0.001
                v2 0.001
                v3 0.001
                c1 -20
                c2 -8
                c3 -8
                d1 1
                d2 1
                d3 1
                f1 80
                f2 1
                f3 80
                clipVal 0.3
                cnt-bus 0
                control-bus 0
                video-control-bus 0
                outbus 0
                bdur 0.5]
  (let [control_in   (in:kr control-bus 10)
        cntr_in      (in:kr cnt-bus)
          gate  (select:kr 0 control_in)
          gate  (* gate cntr_in)
        trg  (trig gate bdur)
        ;trg (t-delay trg (* bdur (pink-noise:kr)) )
          freq  (select:kr 1 control_in)
          a     (select:kr 5 control_in)
          d     (select:kr 6 control_in)
          s     (select:kr 7 control_in)
          r     (select:kr 8 control_in)
          pls   trg
          adj       (max 1 pls)
          co-env    (perc v1 d1 f1 c1)
          a-env     (perc v2 d2 f2 c2)
          osc-env   (perc v3 d3 f3 c3)
          cutoff    (lpf (pink-noise) (+ (env-gen co-env :gate pls) (* 1 20)))
          sound     (lpf (sin-osc (+ 0 (env-gen osc-env :gate pls) 20)) (* 200 1))
          env       (env-gen a-env :gate pls)
          venv      (a2k env)
          _         (out:kr video-control-bus pls)
          output    (*  amp (+ cutoff sound) env)
          output    (free-verb output 0.1 0.3 0.1)
          pls 0
          ;output    (breakcore 0 0 0 0.5)
        ]
      (out outbus (pan2 (* amp_output (clip:ar output clipVal))))))


(do (kill k1)
    (def k1 (kick :control-bus mcbus3 :amp 1 :video-control-bus vcbus3 :cnt-bus b1st_beat-trg-bus))

    )

(ctl k1 :amp 1 :control-bus mcbus3 :video-control-bus vcbus3
     :v1 0.01 :v2 0.01 :v3 0.01
     :d1 1 :d2 10 :d3 1
     :f1 50 :f2 5 :f3 40
     :c1 -20 :c2 -18 :c3 -18 :bdur 0.11111)

(ctl k1 :amp 1 :control-bus mcbus3 :video-control-bus vcbus3
     :v1 0.01 :v2 0.001 :v3 0.001
     :d1 11 :d2 11 :d3 11
     :f1 50 :f2 5 :f3 40
     :c1 -20 :c2 -8 :c3 -8 )


(ctl k1 :amp 1)

(control-bus-get vcbus3)

(stop)

(pp-node-tree)


(defsynth overpad
  [control-bus 0 note 30 amp 0.5 outbus 0 ctrl-output 0]
  (let [control_in   (in:kr control-bus 10)
        gate  (select:kr 0 control_in)
        freq  100 ;(select:kr 1 control_in)
        a     0.1;(select:kr 5 control_in)
        d     0.1;(select:kr 6 control_in)
        s     0.1;(select:kr 7 control_in)
        r     0.1;(select:kr 8 control_in)
        noise (pink-noise)
        env    (env-gen (adsr a d s r) :gate gate)
        f-env (+ freq (* 30 freq (env-gen (perc 0.012 (- r 0.1)))))
        bfreq (/ freq 2)
        sig   (apply +
                     (concat (* 0.7 (saw [bfreq (* 0.99 bfreq)]))
                             (lpf (saw [freq (* noise freq 1.01)]) f-env)))
        ctrl-out (a2k (* env sig))
        _        (out:kr ctrl-output ctrl-out)
        audio (* amp env sig)
        ]
    (out outbus (pan2 audio))))


(do (kill op)
    (def op (overpad  [:tail early-g] :control-bus mcbus3 :ctrl-output vcbus2 :amp 0.1))

    )

(ctl op :amp 4.6)

                                        ;Video
(t/start "./b13.glsl" :width 1920 :height 1080 :cams [0 1] :videos ["../videos/tietoisku_1_fixed.mp4" "../videos/spede_fixed.mp4"  "../videos/vt2.mp4" "../videos/hapsiainen_fixed.mp4" "../videos/sormileikit.mp4"])



(defonce beat-cnt-bus-atom_1 (bus-monitor b1st_beat-cnt-bus))


                                        ;indices 0-50 for control, indiced 51-100 for video selection

(add-watch beat-cnt-bus-atom_1 :cnt (fn [_ _ old new]
                                    (let [])
                                      (t/set-dataArray-item 0 (+ (nth (control-bus-get vcbus1) 0) 0.01) )
                                      (t/set-dataArray-item 1 (+ (nth (control-bus-get vcbus2) 0) 0.01) )
                                      (t/set-dataArray-item 2 (+ (nth (control-bus-get vcbus3) 0) 0.01) )
                                      ;(t/set-dataArray-item 0 (+ (nth (control-bus-get vcbus1) 0) 0.01) )
                                      ;(t/set-dataArray-item 0 (+ (nth (control-bus-get vcbus1) 0) 0.01) )

                                      ))

(control-bus-get vcbus1)

(t/set-dataArray-item 2 10)

(do
                                        ;sepede 51000, 51700
  (t/bufferSection 1 0 51700)

  (t/set-active-buffer-video 1 0)

  (t/set-video-fixed 1 :fw)

  (t/set-video-fps 2 10)

  )

(t/toggle-recording "/dev/video1")

(t/stop)

(stop)


(defsynth asasa [f 100] (out 0 (sin-osc f)))

(def aaaaa (asasa))

(ctl aaaaa :f 440)

(kill aaaaa)
