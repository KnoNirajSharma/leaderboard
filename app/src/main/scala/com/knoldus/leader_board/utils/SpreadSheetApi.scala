package com.knoldus.leader_board.utils

import java.io.{File, FileInputStream, IOException, InputStreamReader}
import java.util.Collections

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeFlow, GoogleClientSecrets}
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.model.ValueRange
import com.google.api.services.sheets.v4.{Sheets, SheetsScopes}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

class SpreadSheetApi(config: Config) extends LazyLogging {
  val APPLICATION_NAME = "Google Sheets API"
  val JSON_FACTORY = JacksonFactory.getDefaultInstance

  /**
   * getting response from spread sheet.
   *
   * @return valuerange of data from spread sheet.
   */
  @throws(classOf[IOException])
  def getResponse: ValueRange = {
    logger.info("getting response from spreadsheet api")
    val spreadsheetId = config.getString("spreadSheetId")
    val range = config.getString("spreadSheetRange")
    val service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport,
      JacksonFactory.getDefaultInstance, getCredentials(GoogleNetHttpTransport.newTrustedTransport))
      .setApplicationName(APPLICATION_NAME).build
    service.spreadsheets.values.get(spreadsheetId, range).execute
  }

  /**
   * Creates an authorized Credential object.
   *
   * @param HTTP_TRANSPORT The network HTTP Transport.
   * @return An authorized Credential object.
   * @throws IOException If the credentials.json file cannot be found.
   */
  @throws(classOf[IOException])
  private def getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential = {
    logger.info("loading credentials from google api")
    val TOKENS_DIRECTORY_PATH = "tokens"
    // Load client secrets.
    val in = new FileInputStream(new File(config.getString("pathForGoogleApiCredentialFile")))
    val clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance, new InputStreamReader(in))
    // Build flow and trigger user authorization request.

    val flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
      Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY))
      .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
      .setAccessType("offline").build
    val port = 8888
    val receiver = new LocalServerReceiver.Builder().setPort(port).build
    new AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
  }
}
