(ns b14 (:use [overtone.live]) (:require [viritystone.tone :as t]) )



(do
  (def b1 (buffer 9))
  (buffer-write! b1 [0 1/8 1/8 1/8 1/8 1/8 1/8 1/8 1/8])
  (def b2 (buffer 5))
  (buffer-write! b2 [0 1/4 1/4 1/4 1/4])
  (def b3 (buffer 3))
  (buffer-write! b3 [0 1/2 1/2])
  (def b4 (buffer 4))
  (buffer-write! b4 [0 1/3 1/3 1/3] )
  (def b5 (buffer 17))
  (buffer-write! b5 [0 1/16 1/16 1/16 1/16 1/16 1/16 1/16 1/16 1/16 1/16 1/16 1/16 1/16 1/16 1/16 1/16])


  (def bp (buffer 5))
  (buffer-write! bp [(buffer-id b1) (buffer-id b2) (buffer-id b3) (buffer-id b4) (buffer-id b5)])
  (def bps (buffer 5))
  (buffer-write! bps [5 3 4 17 9])
  (def tstbus (control-bus 1)))


(def tstbus2 (control-bus 1))

(def debugbus (control-bus 1))

(def tsttriggen (triggerGenerator base-trigger-bus
                                  base-trigger-count-bus
                                  bp
                                  tstbus))

(ctl tsttriggen :base-pattern-buffer-in bp :base-pattern-size-buffer-in bps)

(kill tsttriggen)


(def tsttriggen2 (triggerGenerator base-trigger-bus
                                  base-trigger-count-bus
                                  bp
                                  tstbus2))


(defsynth tstsin [trig-in 0 f 200] (let [trg (in:kr trig-in)
                                   env (env-gen (perc 0.01 0.01 1 0) :gate trg)
                                   src (* env (sin-osc f))]
                               (out 0 src)))


(pp-node-tree)

(kill 108)
(buffer-id b5)
(buffer-free b5)

(def gg (group "tsti"))

(def tstsin1 (tstsin [:tail gg] tstbus))

(kill tstsin1)

(def tstsin2 (tstsin tstbus2 1260))

(ctl tstsin1 :f 244)

(kill tstsin2)

(stop)


(:children ( nth  (node-tree-seq) 0))

(doseq [x (node-tree-seq)] (println x))

(node-get-controls tstsin1 :f)

(to-id tstsin1)

(defprotocol modder
  (set-name [this val])
  (get-name [this]))

(deftype Door [^:volatile-mutable lname]
  modder
  (set-name [this val] (set! lname val))
  (get-name [this] (. this lname)))


(def opnb (Door. "kakka"))

(get-name opnb)

(defprotocol synth-control
  (kill-synth [this])
  (swap-synth [this synth-name])
  (ctl-synth [this var value]))

(deftype synthContainer [^:volatile-mutable synthname]
  synth-control
  (kill-synth [this] (kill (. this synthname)))
  (swap-synth [this synth-name] (println "not implemented"))
  (ctl-synth [this var value] (ctl (. this synthname) var value)))


(def sctl (synthContainer. tstsin1))

(kill-synth sctl)
(ctl-synth sctl :f 210)

(find-var 'kakka)

(intern *ns* (symbol "aaa") 100)

(resolve (symbol "bbb"))


(defn trg [pattern-name synth-name pattern] (let [pattern-name-symbol  (symbol pattern-name)
                                                  resolved-pattern     (resolve pattern-name-symbol)
                                                  type-pattern-name    (if (some? resolved-pattern) (type (var-get resolved-pattern)) nil)
                                                  synth-node-status    (if  (= overtone.sc.node.SynthNode type-pattern-name )
                                                                         (node-live? (var-get resolved-pattern))
                                                                         nil)      ]
                                              (if  synth-node-status
                                                (do (println "Synth exits"))
                                                (do (println "Synth created") (intern *ns* (symbol pattern-name) (synth-name tstbus )) )  )))

; (intern *ns* (symbol pattern-name) pattern-name)
(trg "tstsin1" tstsin 0)

(group "tstsinpattern")

tstsin1

(pp-node-tree)

(idify tstsin1)

(node-tree-matching-synth-ids "tst1")

(node-live? tstsin1)

(some? nil)

(resolve tstsin1)

(= (type tstsin1) overtone.sc.node.SynthNode)

(node-live? (var-get (resolve (symbol "tstsin1"))))

(resolve (symbol "aasasd"))

(pp-node-tree)

                                        ;Clojure patterning functions and trigger synths
(do
  (do
    (defsynth baseTrigger [dur 1 out-bus 0] (out:kr out-bus (trig:kr (impulse:kr (/ 1 (in:kr dur))))))

    (def base-trigger-bus (control-bus 1))

    (def base-trigger-dur-bus (control-bus 1))

    (control-bus-set! base-trigger-dur-bus 1)

    (def base-trigger (baseTrigger base-trigger-dur-bus base-trigger-bus))

    (def base-trigger-count-bus (control-bus 1))

    (defsynth baseTriggerCounter [base-trigger-bus-in 0 base-trigger-count-bus-out 0]
      (out:kr base-trigger-count-bus-out (pulse-count:kr (in:kr base-trigger-bus-in))))

    (def base-trigger-count (baseTriggerCounter base-trigger-bus base-trigger-count-bus))


    (defsynth triggerGenerator [base-trigger-bus-in 0
                                base-counter-bus-in 0
                                base-pattern-buffer-in 0
                                trigger-bus-out 0]
      (let [base-trigger        (in:kr base-trigger-bus-in)
            base-counter        (in:kr base-counter-bus-in)
            pattern-buffer-id   (dbufrd base-pattern-buffer-in base-counter)
            pattern_item        (dbufrd pattern-buffer-id (dseries 0 1 INF) 0)
            trg                 (t-duty:kr  (dbufrd pattern-buffer-id (dseries 0 1 INF) 0)  base-trigger  pattern_item)]
        (out:kr trigger-bus-out trg))))

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


 )



(stop)

(def bub (set-buffer2 ttt bub (generateDurations [[1 1] 1 1 1]) ))


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
  (let [control_in   (in:kr control-bus)
          gate  control_in ;(select:kr 0 control_in)
          ;gate  (* gate cntr_in)
        trg  (trig gate bdur)
        ;trg (t-delay trg (* bdur (pink-noise:kr)) )
          freq  100; (select:kr 1 control_in)
          a     0.01 ;(select:kr 5 control_in)
          d     0.01 ;(select:kr 6 control_in)
          s     0.01 ;(select:kr 7 control_in)
          r     0.01 ;(select:kr 8 control_in)
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
    (def k1 (kick :control-bus tstbus))

    )

(ctl k1 :amp 20
     :v1 0.01 :v2 0.01 :v3 0.01
     :d1 1 :d2 10 :d3 1
     :f1 50 :f2 5 :f3 40
     :c1 -20 :c2 -18 :c3 -18 :bdur 0.11111)

(ctl k1 :amp 1
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
  (let [control_in   (in:kr control-bus)
        gate  control_in
        freq  100 ;(select:kr 1 control_in)
        a     0.015;(select:kr 5 control_in)
        d     0.05;(select:kr 6 control_in)
        s     0.05;(select:kr 7 control_in)
        r     0.05;(select:kr 8 control_in)
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
    (def op (overpad  :control-bus tstbus :amp 0.))

    )

(ctl op :amp 40.6)

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
