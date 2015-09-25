(ns scratch.rocket-test
  (:require [clojure.test :refer :all]
            [scratch.rocket :refer :all]))

(deftest spherical-coordinate-test
  (let [pos {:x 1 :y 2 :z 3}]
    (testing "spherical->cartesian"
      (is (= (spherical->cartesian {:r 2
                                    :phi 0
                                    :theta 0})
             {:x 0.0 :y 0.0 :z 2.0})))

    (testing "roundtrip"
      (is (= pos (-> pos cartesian->spherical spherical->cartesian))))))

(deftest makes-orbit
  (let [trajectory (->> (atlas-v)
                        prepare
                        (trajectory 1))]
    (when (crashed? trajectory)
      (println "Crashed at" (crash-time trajectory) "seconds")
      (println "Maximum altitude" (apoapsis trajectory)
                "meters at"       (apoapsis-time trajectory) "seconds"))

    ; Assert that teh rocket eventually made it to orbit
    (is (not (crashed? trajectory)))))
