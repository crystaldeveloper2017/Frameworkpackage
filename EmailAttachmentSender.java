package com.crystal.Frameworkpackage;



public class EmailAttachmentSender {
	/*
	 * 
	 * public static void sendEmailWithAttachments(String host, String port, final
	 * String userName, final String password, String toAddress, String subject,
	 * String message, String[] attachFiles) throws AddressException,
	 * MessagingException { // sets SMTP server properties Properties properties =
	 * new Properties(); properties.put("mail.smtp.host", host);
	 * properties.put("mail.smtp.port", port); properties.put("mail.smtp.auth",
	 * "true"); properties.put("mail.smtp.starttls.enable", "true");
	 * properties.put("mail.user", userName); properties.put("mail.password",
	 * password);
	 * 
	 * // creates a new session with an authenticator Authenticator auth = new
	 * Authenticator() { public PasswordAuthentication getPasswordAuthentication() {
	 * return new PasswordAuthentication(userName, password); } }; Session session =
	 * Session.getInstance(properties, auth);
	 * 
	 * // creates a new e-mail message Message msg = new MimeMessage(session);
	 * 
	 * msg.setFrom(new InternetAddress(userName)); InternetAddress[] toAddresses = {
	 * new InternetAddress(toAddress) }; msg.setRecipients(Message.RecipientType.TO,
	 * toAddresses); msg.setSubject(subject); msg.setSentDate(new Date());
	 * 
	 * // creates message part MimeBodyPart messageBodyPart = new MimeBodyPart();
	 * messageBodyPart.setContent(message, "text/html");
	 * 
	 * // creates multi-part Multipart multipart = new MimeMultipart();
	 * multipart.addBodyPart(messageBodyPart);
	 * 
	 * // adds attachments if (attachFiles != null && attachFiles.length > 0) { for
	 * (String filePath : attachFiles) { MimeBodyPart attachPart = new
	 * MimeBodyPart();
	 * 
	 * try { attachPart.attachFile(filePath,
	 * Files.probeContentType(Paths.get(filePath)), null);
	 * 
	 * ; } catch (IOException ex) { ex.printStackTrace(); }
	 * 
	 * multipart.addBodyPart(attachPart); } }
	 * 
	 * // sets the multi-part as e-mail's content msg.setContent(multipart);
	 * 
	 * // sends the e-mail Transport.send(msg);
	 * 
	 * }
	 * 
	 *//**
		 * Test sending e-mail with attachments
		 */
	/*
	 * public static void main(String[] args) { // SMTP info String host =
	 * "smtp.gmail.com"; String port = "587"; String mailFrom = "Info@ssegpl.com";
	 * String password = "Password@2022";
	 * 
	 * // message info String mailTo = "crystaldevelopers2017@gmail.com"; String
	 * subject = "Test Multiple Attachment Updated Library"; String message =
	 * "I have some attachments for you.";
	 * 
	 * // attachments String[] attachFiles = new String[2]; attachFiles[0] =
	 * "C:\\Test2.pdf"; attachFiles[1] = "C:\\1.txt";
	 * 
	 * try { sendEmailWithAttachments(host, port, mailFrom, password, mailTo,
	 * subject, message, attachFiles); System.out.println("Email sent."); } catch
	 * (Exception ex) { System.out.println("Could not send email.");
	 * ex.printStackTrace(); } }
	 */}
