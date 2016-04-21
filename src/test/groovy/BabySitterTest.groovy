import spock.lang.*
import org.joda.time.format.*

class BabySitterTest extends Specification {
  def "When a baby sitter works from 5-6 PM, he or she should be paid \$12"() {
    given: "A baby sitter"
      def babySitter = new BabySitter()
    when: "The baby sitter works from 5-6 PM"
      def paymentDue = buildDatesAndCalculatePayment(babySitter,'2016-04-01T17:00:00-05','2016-04-01T18:00:00-05')
    then: "He or she should be paid \$12"
      paymentDue == 12
  }

  def """When a baby sitter starts their shift after they end their shift an
    IllegalArgumentException should be thrown"""() {
    given: "A baby sitter"
      def babySitter = new BabySitter()
    when: "The baby sitter's start time falls before their end time"
      def paymentDue = buildDatesAndCalculatePayment(babySitter,'2016-04-01T18:00:00-05','2016-04-01T17:00:00-05')
    then: "An IllegalArgumentException should be thrown"
      thrown IllegalArgumentException
  }

  def "A baby sitter should not start their shift before 5 PM"() {
    given: "A baby sitter"
      def babySitter = new BabySitter()
    when: "The baby sitter's start time is before 5 PM"
      def paymentDue = buildDatesAndCalculatePayment(babySitter,'2016-04-01T16:00:00-05','2016-04-01T18:00:00-05')
    then: "An IllegalArgumentException should be thrown"
      thrown IllegalArgumentException
  }

  def buildDatesAndCalculatePayment(BabySitter babySitter, 
                                    String startTimeISO8601, 
                                    String endTimeISO8601) {
      def isoDateTimeParser = ISODateTimeFormat.dateTimeParser()
      def startTime = isoDateTimeParser.parseLocalDateTime(startTimeISO8601)
      def endTime = isoDateTimeParser.parseLocalDateTime(endTimeISO8601)
      babySitter.calculatePaymentDue(startTime,endTime)
  }
}
