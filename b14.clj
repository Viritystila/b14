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
    (control-bus-set! master-rate-bus (* 1 1))
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


(def cb1 (control-bus))

(def cb2 (control-bus))

(defsynth tst [trig-in 0 counter-in 0 dur-buffer-in 0 out-bus 0 cntrl-bus 0 b 4] (let [trg-in       (in:kr trig-in)
                                                                                         ;   cntr-in      (in:kr counter-in)
                                        ;trg (demand:kr (in:kr trg-in) 0 (dbufrd dur-buffer-in cntr-in) )
                                                                                            trg (t-duty:kr (dbufrd 175 (dseries 0 1 b) 0) trg-in)
                                                                                            env (env-gen (perc 0.01 0.01 1 0) :gate trg)
                                                                                            ] (out:kr out-bus trg)
                                                                                  (out 0 (* 1 env (sin-osc (* 1 200  ))))))

(def bbbb (buffer 3))



(index (buffer-id bbbb) 30)

(odoc index)

(odoc t-duty)

(odoc dbufrd)

(odoc dseries)

(odoc buffer-data)

                                        ;Clojure patterning functions and trigger synths
(do
  (do
    (defsynth baseTrigger [dur 1 out-bus 0] (out:kr out-bus (impulse:kr (in:kr dur))))
    (def base-trigger-bus (control-bus 1))
    (def base-trigger-dur-bus (control-bus 1))
    (control-bus-set! base-trigger-dur-bus 1)
    (def base-trigger (baseTrigger base-trigger-dur-bus base-trigger-bus))

    (def base-trigger-count-bus (control-bus 1))
    (defsynth baseTriggerCounter [base-trigger-bus-in 0 base-trigger-count-bus-out 0]
      (out:kr base-trigger-count-bus-out (pulse-count:kr (in:kr base-trigger-bus-in))))
    (def base-trigger-count (baseTriggerCounter base-trigger-bus base-trigger-count-bus)))


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
                                              (let [;_ (println xv)
                                                    length (count input-vec)
                                                    x (first xv)]
                                                (if (vector? x) (recur (next xv) (conj result (traverseVector x length)))
                                                    (recur (next xv) (conj result (/ 1 length 1))))) result)))))
    ([input-array bl] (let [input-vec input-array
                                        ;_ (println bl)
                            ]
                        (if (vector? input-vec)
                          (loop [xv (seq input-vec)
                                 result []]
                            (if xv
                              (let [length (count input-vec)
                                    x (first xv)]
                                (if (vector? x) (recur (next xv) (conj result (traverseVector x (* bl length))))
                                    (recur (next xv) (conj result (/ 1 length bl))))) result))))))


  (defn sumZeroDurs [idxs input-vector full-durs] (loop [xv (seq idxs)
                                                         sum 0]
                                                    (if xv
                                                      (let [x       (first xv)
                                                            zero-x  (nth input-vector x )
                                                            dur-x   (nth full-durs x)]
                                                        (println zero-x)
                                                        (println dur-x)
                                                        (if (= zero-x 0) (do (recur (next xv) (+ dur-x sum))) sum)) sum)))


  (defn adjustDuration [input-vector input-original] (let [length   (count input-vector)
                                                           full-durs input-vector
                                        ;_ (println full-durs)
                                                           input-vector (into [] (map * input-vector input-original))
                                                           idxs (vec (range length))]
                                                       (loop [xv (seq idxs)
                                                              result []]
                                                         (if xv
                                                           (let [xidx      (first xv)
                                                                 nidx      (mod (+ 1 xidx) length)
                                                                 opnext    (nth input-vector nidx)
                                                                 op        (nth input-vector xidx)
                                                                 vec-ring  (flatten (conj (subvec idxs nidx) (subvec idxs 0 nidx )))
                                        ;_  (println (subvec input-vector nidx))
                                        ;_ (println (countZeros (subvec input-vector nidx)))
                                                                 op      (if (and (not= 0 op) ( = 0 opnext)) (+ op (sumZeroDurs vec-ring input-vector full-durs)) op)]
                                                             (recur (next xv) (conj result op))) result))))

  (defn generateDurations [input] (let [mod-input (vec (map triggerDur (vec (flatten input))))
                                        durs  (traverseVector input)
                                        durs  (into [] (flatten durs))
                                        durs  (adjustDuration durs (vec (flatten mod-input)))]
                                        ;(println durs)
                                    durs) )

  (defn set-buffer [synth-in buf new-buf-data] (let [size         (count new-buf-data)
                                                      new-buf      (buffer size) ]
                                                  (buffer-write-relay! new-buf new-buf-data)
                                                  (ctl synth-in :dur-buffer-in new-buf)
                                                  (buffer-free buf)
                                                  new-buf
                                                  ))

  (defn set-buffer2 [synth-in buf & new-buf-data] (let [;new-buf-data (vec (concat new-buf-data))
                                                        new-buf-data (vec (flatten (vec new-buf-data)))
                                                        size         (count new-buf-data)
                                                      new-buf      (buffer size) ]
                                                  (buffer-write-relay! new-buf new-buf-data)
                                                  (ctl synth-in :dur-buffer-in new-buf)
                                                  (buffer-free buf)
                                                  new-buf
                                                  ))






  (event-monitor-off)


  (reduce + (generateDurations [1 1 1 [1 1 1 1]])))


(def bub (set-buffer2 ttt bub (generateDurations [[1 1] 1 1 1]) ))

(buffer-id bub)

(repeat 10 1)

(concat [1 2 3] [1 2 3] [1 2 3])


(do
  (kill ttt)
  (def ttt (tst base-trigger-bus b32th_beat-cnt-bus bub cb1 0))

  )

(= overtone.sc.synth.Synth (type tst))

(buffer-id bub)


(ctl ttt :b 4)

(control-bus-get cb1)

(control-bus-set! cb2 3)

(pp-node-tree)

(kill 73)

(kill ttt)

(kill ttt2)


(subvec [1 2 3] 4)

(defsynth ts [] (out 0 (sin-osc 100)))

(def tss (ts))

(kill tss)

(odoc buf-rd)

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
