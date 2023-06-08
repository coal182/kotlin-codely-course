package com.codely.demo

import com.codely.demo.shared.Clock
import com.codely.demo.shared.Reader
import com.codely.demo.shared.Writer

class AppTest(reader: Reader, writer: Writer, clock: Clock) : App(reader, writer, clock)
