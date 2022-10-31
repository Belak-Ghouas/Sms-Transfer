package com.sms.pipe.view.model

import com.sms.pipe.R

data class Step (var position:Int, var status:StepStatus){

    fun next(){
        status = when(status){
            StepStatus.DONE -> StepStatus.DONE
            StepStatus.IN_PROGRESS -> StepStatus.DONE
            StepStatus.NOT_DONE -> StepStatus.IN_PROGRESS
        }
    }

    fun getBackground():Int{
        return when(status){
            StepStatus.IN_PROGRESS -> R.drawable.step_inprogress
            StepStatus.DONE -> R.drawable.step_done
            StepStatus.NOT_DONE -> R.drawable.step_not_done
        }
    }

    fun getIcon():Int{
        return when(status){
            StepStatus.IN_PROGRESS ,StepStatus.NOT_DONE-> R.drawable.ic_circle
            StepStatus.DONE -> R.drawable.ic_done
        }
    }

    fun getPadding():Int{
        return when(status){
            StepStatus.IN_PROGRESS -> 10
            StepStatus.NOT_DONE-> 16
            StepStatus.DONE -> 2
        }
    }
}



enum class StepStatus{
    DONE,IN_PROGRESS,NOT_DONE
}