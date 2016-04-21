import com.google.common.base.Preconditions
import org.joda.time.DateTime

class BabySitter {
  /**
    Calculates the nightly payment due to this baby sitter give the specified
    start and end times

    @param startTime the time at which the baby sitter's shift began
    @param endTime the time at which the baby sitter's shift ended
    @return an integer representing the payment due to this baby sitter for
      working the specified hours
    */
  def calculatePaymentDue(DateTime startTime, DateTime endTime) {
    validatePreconditions(startTime, endTime)
    12
  }

  def validatePreconditions(DateTime startTime, DateTime endTime) {
    Preconditions.checkArgument(startTime < endTime, """The start time 
                                ($startTime) must be before the end time 
                                ($endTime)""")
  }
}
