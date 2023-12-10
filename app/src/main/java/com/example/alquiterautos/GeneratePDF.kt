package com.example.alquiterautos

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.alquiterautos.data.InfoCar
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun generatePDF(context: Context, directory: File, cars: List<InfoCar>, balance: List<Int>) {

    val maxBalance = balance.maxOrNull() ?: 0
    val indexMaxBalance = balance.indexOf(maxBalance)

    val pageHeight = 1120
    val pageWidth = 700
    val pdfDocument = PdfDocument()
    val title = Paint()
    val myPageInfo = PageInfo.Builder(pageWidth, pageHeight, 1).create()
    val myPage = pdfDocument.startPage(myPageInfo)
    val canvas: Canvas = myPage.canvas
    title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    title.textSize = 15f
    title.color = ContextCompat.getColor(context, R.color.black)
    canvas.drawText("Reporte de simulación", 300f, 80f, title)
    title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    title.color = ContextCompat.getColor(context, R.color.black)
    title.textSize = 15f
    title.textAlign = Paint.Align.CENTER
    canvas.drawText(
        "Tras llevar a cabo la simulación, se concluyó que la cantidad óptima de automóviles ",
        320f,
        120f,
        title
    )
    canvas.drawText(
        "a adquirir es ${indexMaxBalance + 1}, determinada de la siguiente manera:",
        220f,
        140f,
        title
    )

    canvas.drawText(
        "Cantidad de autos - Utilidad Neta : ",
        230f,
        170f,
        title
    )

    repeat(cars.size) {
        val car = cars[it]

        canvas.drawText(
            "${it + 1} auto${if(it == 0) "" else "s"} - ${car.accBalance} bs",
            250f,
            195f + (20f * it),
            title
        )
    }

    canvas.drawText(
        "En la siguiente sección se presentan los modelos de autos alquilados, categorizados",
        320f,
        300f,
        title
    )
    canvas.drawText(
        "por la cantidad de unidades, junto con su información financiera correspondiente:",
        309f,
        320f,
        title
    )

    canvas.drawText(
        "Cantidad de autos - Modelos : ",
        230f,
        360f,
        title
    )

    repeat(cars.size) {
        val car = cars.subList(0, it + 1).joinToString(", ") { car -> car.model }

        canvas.drawText(
            "${it + 1} auto${if (it == 0) "" else "s"} - $car",
            400f - (60f * (4 - it)),
            390f + (20f * it),
            title
        )
    }


    repeat(cars.size) {
        val car = cars[it]

        canvas.drawText(
            "Modelo ${it +1}: ${car.model}",
            170f,
            500f + (100f * it),
            title
        )
        canvas.drawText(
            "Costo del modelo ${it +1}: ${car.price} bs",
            170f,
            520f + (100f * it),
            title
        )
        canvas.drawText(
            "Costo ocio del modelo ${it +1}: ${car.leisureCost} bs",
            170f,
            540f + (100f * it),
            title
        )
        canvas.drawText(
            "Costo no disponible ${it +1}: ${car.availableCost} bs",
            170f,
            560f + (100f * it),
            title
        )
    }

//    canvas.drawText(
//        "Modelo - Ingreso por alquile - Costo ocio - Costo no disponible",
//        240f,
//        500f,
//        title
//    )
//
//    repeat(cars.size) {
//        val car = cars[it]
//
//        canvas.drawText(
//            "${car.model} - ${car.price} bs - ${car.leisureCost} bs - ${car.availableCost} bs",
//            260f,
//            540f + (20f * it),
//            title
//        )
//    }


    pdfDocument.finishPage(myPage)
    val fileName: String = System.currentTimeMillis().toString().replace(":", ".") + ".pdf"
    val file = File(directory, fileName)

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        Toast.makeText(context, "PDF file generated successfylly", Toast.LENGTH_SHORT).show()
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
    pdfDocument.close()
}

fun drawableToBitmap(drawable: Drawable): Bitmap? {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }
    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

