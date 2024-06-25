package com.nitamlII.heart_uasii.ui.simulasi

import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.nitamlII.heart_uasii.databinding.FragmentSimulasiBinding
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class SimulasiFragment : Fragment() {

    private lateinit var binding: FragmentSimulasiBinding
    private lateinit var interpreter: Interpreter
    private val mModelPath = "heart.tflite"
    private val viewModel: SimulasiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Menggunakan View Binding
        binding = FragmentSimulasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load TFLite model
        initInterpreter()

        // List of items to display in AutoCompleteTextView
        val genderItems = listOf("0 - Pria", "1 - Wanita")
        val chestItems = listOf("0 - Typical Angina", "1 - Atypical Angina", "2 - Non-anginal Pain", "3 - Asymptomatic")
        val smokingItems = listOf("0 - Tidak Pernah", "1 - Dulu", "2 - Ya")
        val alcoholItems = listOf("0 - Tidak Ada", "1 - Sedang", "2 - Tinggi")
        val yesNoItems = listOf("0 - Tidak", "1 - Ya")

        // Set the adapter to AutoCompleteTextView using binding
        setupAutoCompleteTextView(binding.autoCompletegender, genderItems)
        setupAutoCompleteTextView(binding.autoCompletechest, chestItems)
        setupAutoCompleteTextView(binding.autoCompletesmoking, smokingItems)
        setupAutoCompleteTextView(binding.autoCompletealcohol, alcoholItems)

        setupAutoCompleteTextView(binding.autoCompleteyesno1, yesNoItems)
        setupAutoCompleteTextView(binding.autoCompleteyesno2, yesNoItems)
        setupAutoCompleteTextView(binding.autoCompleteyesno3, yesNoItems)
        setupAutoCompleteTextView(binding.autoCompleteyesno4, yesNoItems)

        // Observe result from ViewModel
        viewModel.result.observe(viewLifecycleOwner) { result ->
            Log.e("result", result.toString())
            val diagnosis = when {
                result!! > 0.5 -> "Pasien Menderita Penyakit Jantung"
                else -> "Pasien Tidak Menderita Penyakit Jantung"
            }
            binding.txtResult.text = diagnosis
        }

        // Predict button
        binding.btnCheck.setOnClickListener {
            try {
                // Extract input data from TextInputEditText and AutoCompleteTextView
                val input = floatArrayOf(
                    binding.question1.editText?.text.toString().split(" - ")[0].toFloat(),
                    binding.autoCompletegender.text.toString().split(" - ")[0].toFloat(),
                    binding.question3.editText?.text.toString().split(" - ")[0].toFloat(),
                    binding.question4.editText?.text.toString().split(" - ")[0].toFloat(),
                    binding.question5.editText?.text.toString().split(" - ")[0].toFloat(),
                    binding.autoCompletesmoking.text.toString().split(" - ")[0].toFloat(),
                    binding.autoCompletealcohol.text.toString().split(" - ")[0].toFloat(),
                    binding.question8.editText?.text.toString().split(" - ")[0].toFloat(),
                    binding.autoCompleteyesno1.text.toString().split(" - ")[0].toFloat(),
                    binding.autoCompleteyesno2.text.toString().split(" - ")[0].toFloat(),
                    binding.autoCompleteyesno3.text.toString().split(" - ")[0].toFloat(),
                    binding.slider.value,
                    binding.question13.editText?.text.toString().split(" - ")[0].toFloat(),
                    binding.autoCompleteyesno4.text.toString().split(" - ")[0].toFloat(),
                    binding.question15.editText?.text.toString().split(" - ")[0].toFloat(),
                    )

                // Lakukan inferensi
                Log.e("lol",input.contentToString());
                val result = doInference(input)
                viewModel.setResultValue(result)

            } catch (e: Exception) {
                Log.e("Prediction Error", "Error during prediction: ", e)
                Toast.makeText(context, "Error during prediction: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initInterpreter() {
        val options = Interpreter.Options()
        options.setNumThreads(6)
        options.setUseNNAPI(true)
        interpreter = Interpreter(loadModelFile(requireContext().assets, mModelPath), options)
    }

    private fun doInference(input: FloatArray): Float {
        val output = Array(1) { FloatArray(1) }
        interpreter.getOutputTensor(0).shape()
        interpreter.run(input, output)
        return output[0][0]
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun setupAutoCompleteTextView(autoCompleteTextView: AutoCompleteTextView, items: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        autoCompleteTextView.setAdapter(adapter)
    }

}