package com.example.a7minutesworkout

import com.example.a7minutesworkout.model.ExerciseModel

object Constants {

    fun defaultExerciseList():ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJack=ExerciseModel(1, "JumpingJack", R.drawable.ic_jumping_jacks, false, false)
        exerciseList.add(jumpingJack)

        val wallSit=ExerciseModel(2, "wallSit", R.drawable.ic_wall_sit, false, false)
        exerciseList.add(wallSit)

        val pushUp=ExerciseModel(3, "pushUp", R.drawable.ic_push_up, false, false)
        exerciseList.add(pushUp)

        val abdominalCrunch=ExerciseModel(4, "abdominalCrunch", R.drawable.ic_abdominal_crunch, false, false)
        exerciseList.add(abdominalCrunch)

        val setUpOnChair=ExerciseModel(5, "SetUpOnChair", R.drawable.ic_step_up_onto_chair, false, false)
        exerciseList.add(setUpOnChair)

        val squat=ExerciseModel(6, "squat", R.drawable.ic_squat, false, false)
        exerciseList.add(squat)

        val plank=ExerciseModel(7, "plank", R.drawable.ic_plank, false, false)
        exerciseList.add(plank)

        val highKneesRunning=ExerciseModel(8, "highKneesRunning", R.drawable.ic_high_knees_running_in_place, false, false)
        exerciseList.add(highKneesRunning)

        val lunges=ExerciseModel(9, "lunges", R.drawable.ic_lunge, false, false)
        exerciseList.add(lunges)

        val pushUpAndRotation=ExerciseModel(10, "pushUpAndRotation", R.drawable.ic_push_up_and_rotation, false, false)
        exerciseList.add(pushUpAndRotation)

        val sidePlank=ExerciseModel(11, "sidePlank", R.drawable.ic_side_plank, false, false)
        exerciseList.add(sidePlank)

        val tricepsOnChair=ExerciseModel(12, "TricepsOnChair", R.drawable.ic_triceps_dip_on_chair, false, false)
        exerciseList.add(tricepsOnChair)


        return exerciseList
    }

}