package com.github.poluka.kControlLibrary.sender

data class ProgramStatus(
        var status: ProgramStatusEnum = ProgramStatusEnum.READY_TO_SEND,
        val error: String = "")