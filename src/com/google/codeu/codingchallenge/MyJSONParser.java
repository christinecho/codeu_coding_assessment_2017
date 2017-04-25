// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/*
 * Code to parse a JSON-lite object. Not completely done, but I am willing to learn and improve
 * from my mistakes.
 */
package com.google.codeu.codingchallenge;

import java.io.IOException;
import java.util.Stack;

final class MyJSONParser implements JSONParser {

  @Override
  public JSON parse(String in) throws IOException {
	in = in.trim();
	JSON obj = new MyJSON();
	
	// check that input is valid
	if (!(checkBalance(in) && checkEscapableCharacters(in))) {
		throw new IOException();
	}
	if (!(in.charAt(0) == '{' && in.charAt(in.length()-1) == '}')) {
		throw new IOException("Starting and/or ending character does not end in correct bracket");
	}
	if (in.length() < 2) {
		throw new IOException("Length is too short");
	}
	
	in = deleteWhiteSpaces(in);
	in = in.substring(1, in.length()-1);
	for(int i=0; i<in.length();i++) {
		char c = in.charAt(i);
		if (c == ':') {
			if (in.charAt(i+1) == '"') {
				
			} else if (in.charAt(i+1) == '{') {
				
			} else {
				throw new IOException("A key does not have a value");
			}
		}
	}
    return new MyJSON();
  }
  
  
  
  /*Checking if parentheses, square brackets, and curly brackets are balanced
  	Presence of parentheses or brackets in the string was not noted in instructions 
    but added in code just in case
  */
  private boolean checkBalance(String str) {
	 Stack<Character> stack = new Stack<Character>();
	 for (int i=0; i<str.length(); i++) {
		 char currentChar = str.charAt(i);
		 if (currentChar == '{' || currentChar == '(' || currentChar == '[') {
			 stack.push(currentChar);
		 } else if (currentChar == '}') {
			 if (!stack.isEmpty() && stack.pop() == '{') {
				 return true;
			 }
		 } else if (currentChar == ')') {
			 if (!stack.isEmpty() && stack.pop() == '(') {
				 return true;
			 }
		 } else if (currentChar == '[') {
			 if (!stack.isEmpty() && stack.pop() == ']') {
				 return true;
			 }
		 }
	 }
	 
	 if (stack.isEmpty()) {
		 return true;
	 }
	 return false;
  }
  
  private boolean checkEscapableCharacters(String str) {
	  for (int i=0; i<str.length()-1; i++) {
		  char currentChar = str.charAt(i);
		  if (currentChar == '\\') {
			  currentChar = str.charAt(i+1);
			  if (currentChar != 't' && currentChar != 'n') {
				  return false;
			  }
		  }
	  }
	  return true;
  }
  
  private String deleteWhiteSpaces(String str) {
	  StringBuilder stringBuilder = new StringBuilder();
	  for (int i=0; i<str.length(); i++) {
		  char currentChar = str.charAt(i);
		  if (currentChar != ' ') {
			  stringBuilder.append(currentChar);
		  }
	  }
	  return stringBuilder.toString();
  }
}
