package com.ottogi.be.meeting.exception;

import com.ottogi.be.common.exception.CustomException;

public class CannotCreateMeetingException extends CustomException {
    public CannotCreateMeetingException() {
        super(MeetingExceptionConstants.CANNOT_CREATE_MEETING);
    }
}
