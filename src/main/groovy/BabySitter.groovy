import com.google.common.base.Preconditions
import org.joda.time.LocalDateTime
import org.joda.time.Days
import org.joda.time.Hours

class BabySitter {
  private static final STANDARD_RATE = 12
  private static final POST_BEDTIME_RATE = 8
  private static final LATE_NIGHT_RATE = 16
  /**
    Calculates the nightly payment due to this baby sitter give the specified
    start and end times

    @param startTime the time at which the baby sitter's shift began
    @param endTime the time at which the baby sitter's shift ended
    @return an integer representing the payment due to this baby sitter for
      working the specified hours
    */
  def calculatePaymentDue(LocalDateTime startTime, LocalDateTime endTime,
                          int bedTimeHour = 21) {
    validatePreconditions(startTime, endTime)
    // since the baby sitter will not be paid for partial hours,
    def intermediateTime = startTime.withMinuteOfHour(0).withSecondOfMinute(0).
      withMillisOfSecond(0)
    def cumulativePayment = 0
    // walk the time foward from startTime to endTime in 1 hour increments
    while (intermediateTime < endTime) {
      // if the day hasn't rolled over
      if (intermediateTime.getDayOfMonth() == startTime.getDayOfMonth() &&
          intermediateTime.getHourOfDay() < bedTimeHour) {
        cumulativePayment += STANDARD_RATE
      } else if (intermediateTime.getHourOfDay() >= bedTimeHour) {
        cumulativePayment += POST_BEDTIME_RATE
      } else {
        cumulativePayment += LATE_NIGHT_RATE
      }
      intermediateTime = intermediateTime.plusHours(1)
    }
    cumulativePayment
  }

  def validatePreconditions(LocalDateTime startTime, LocalDateTime endTime) {
    Preconditions.checkArgument(startTime < endTime, """The shift's start time
                                ($startTime) must be before the end time
                                ($endTime)""")
    Preconditions.checkArgument(startTime.getHourOfDay() >= 17, """The shift's start time
                                ($startTime) must be on or after 5 PM""")
    // If the shift has rolled over to the next day, it must end by 4 AM
    if (Days.daysBetween(startTime.toLocalDate(),
                         endTime.toLocalDate()).getDays() == 1) {
      Preconditions.checkArgument(endTime.getHourOfDay() < 4 ||
                                  (endTime.getHourOfDay() == 4 &&
                                   endTime.getMinuteOfHour() <= 0 &&
                                   endTime.getSecondOfMinute() <= 0 &&
                                   endTime.getMillisOfSecond() <= 0), """The shift's
                                end time ($endTime) must be on or before 4 AM""")
    }
    def hoursBetweenStartAndEndTime = Hours.hoursBetween(startTime, endTime).getHours()
    Preconditions.checkArgument(hoursBetweenStartAndEndTime <= 11, """The shift
                                must be a total of 11 or fewer consecutive hours""")
  }
}
