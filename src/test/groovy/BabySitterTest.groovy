import spock.lang.*
import org.joda.time.format.*

class BabySitterTest extends Specification {
  def "When a baby sitter works from 5-6 PM, he or she should be paid \$12"() {
    given: "A baby sitter"
      def babySitter = new BabySitter()
      def isoDateTimeParser = ISODateTimeFormat.dateTimeParser()
    when: "The baby sitter works from 5-6 PM"
      def startTime = isoDateTimeParser.parseLocalDateTime('2016-04-01T17:00:00-05')
      def endTime = isoDateTimeParser.parseLocalDateTime('2016-04-01T18:00:00-05')
      def paymentDue = babySitter.calculatePaymentDue(startTime,endTime)
    then: "He or she should be paid \$12"
      paymentDue == 12
  }

  def """When a baby sitter starts their shift after they end their shift an
    IllegalArgumentException should be thrown"""() {
    given: "A baby sitter"
      def babySitter = new BabySitter()
      def isoDateTimeParser = ISODateTimeFormat.dateTimeParser()
    when: "The baby sitter's start time falls before their end time"
      def startTime = isoDateTimeParser.parseLocalDateTime('2016-04-01T18:00:00-05')
      def endTime = isoDateTimeParser.parseLocalDateTime('2016-04-01T17:00:00-05')
      def paymentDue = babySitter.calculatePaymentDue(startTime,endTime)
    then: "An IllegalArgumentException should be thrown"
      thrown IllegalArgumentException
  }

  def "A baby sitter should not start their shift before 5 PM"() {
    given: "A baby sitter"
      def babySitter = new BabySitter()
      def isoDateTimeParser = ISODateTimeFormat.dateTimeParser()
    when: "The baby sitter's start time is before 5 PM"
      def startTime = isoDateTimeParser.parseLocalDateTime('2016-04-01T16:00:00-05')
      def endTime = isoDateTimeParser.parseLocalDateTime('2016-04-01T18:00:00-05')
      def paymentDue = babySitter.calculatePaymentDue(startTime,endTime)
    then: "An IllegalArgumentException should be thrown"
      thrown IllegalArgumentException
  }
}
