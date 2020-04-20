%dw 2.0
output application/java
var messageQ = vars.originalPayload
var messageMT = messageQ.^['mimeType']
---
{
	delaySeconds: 0,
	body: write(messageQ, messageMT),
	messageAttributes: {
		"correlationId": {
			"stringValue" : vars.messageCorrelationId,
			"dataType" : "String.correlationId"
		} as Object {
			class: "org.mule.extension.sqs.api.model.MessageAttributeValue"
		},
		"transactionId": {
			"stringValue" : vars.transactionId,
			"dataType" : "String.transactionId"
		} as Object {
			class : "org.mule.extension.sqs.api.model.MessageAttributeValue"
		},
		"transactionStartTime": {
			"stringValue" : vars.transactionStartTime,
			"dataType" : "String.transactionStartTime"
		} as Object {
			class : "org.mule.extension.sqs.api.model.MessageAttributeValue"
		}
	} as Object {
		class: "java.util.HashMap"
	}	
} as Object {
	class: "org.mule.extension.sqs.api.model.Message"
}