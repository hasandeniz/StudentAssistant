package com.hasandeniz.studentassistant2.offlineCourses.courseDetails.view

import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hasandeniz.studentassistant2.R
import com.hasandeniz.studentassistant2.databinding.BottomSheetDeleteBinding
import com.hasandeniz.studentassistant2.databinding.BottomSheetSetObjectiveBinding
import com.hasandeniz.studentassistant2.databinding.FragmentOfflineCourseDetailsBinding
import com.hasandeniz.studentassistant2.grades.base.data.model.Grade
import com.hasandeniz.studentassistant2.offlineCourses.courseDetails.adapter.OfflineCourseDetailsGradeAdapter
import com.hasandeniz.studentassistant2.offlineCourses.courseDetails.viewModel.OfflineCourseDetailsViewModel
import com.hasandeniz.studentassistant2.offlineCourses.util.OfflineCourseUtil
import dagger.hilt.android.AndroidEntryPoint
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.roundToInt

@AndroidEntryPoint
class OfflineCourseDetailsFragment : Fragment() {
    private var _binding: FragmentOfflineCourseDetailsBinding? = null
    private val binding get() = _binding!!
    private var courseUuid = 0
    private val args: OfflineCourseDetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<OfflineCourseDetailsViewModel>()
    private val adapter = OfflineCourseDetailsGradeAdapter()
    private lateinit var average: String
    private var objective = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfflineCourseDetailsBinding.inflate(inflater, container, false)
        courseUuid = args.courseUuid
        viewModel.getOfflineCourse(courseUuid)
        viewModel.getOfflineCourseGrades(courseUuid)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_course_details, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_edit -> {
                        val offlineCourse = viewModel.offlineCourseLiveData.value
                        if (offlineCourse != null) {
                            val action = OfflineCourseDetailsFragmentDirections
                                .actionOfflineCourseDetailsFragmentToEditOfflineCourseFragment(offlineCourse)
                            Navigation.findNavController(view).navigate(action)
                        }

                        true
                    }

                    R.id.menu_delete -> {
                        val offlineCourse = viewModel.offlineCourseLiveData.value ?: return false
                        val deleteBottomSheet = BottomSheetDialog(requireContext())
                        val bottomSheetBinding = BottomSheetDeleteBinding.inflate(layoutInflater)
                        deleteBottomSheet.setContentView(bottomSheetBinding.root)
                        bottomSheetBinding.tvTitle.text = getString(R.string.delete_course)
                        bottomSheetBinding.tvMessage.text = getString(R.string.delete_course_message)
                        bottomSheetBinding.btnCancel.setOnClickListener {
                            deleteBottomSheet.dismiss()
                        }
                        bottomSheetBinding.btnDelete.setOnClickListener {
                            OfflineCourseUtil.handleSharedPrefCleaning(offlineCourse, requireActivity())
                            viewModel.deleteOfflineCourse(courseUuid)
                            deleteBottomSheet.dismiss()
                            val navController = findNavController()
                            navController.navigateUp()
                        }
                        deleteBottomSheet.show()

                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner)

        observeLiveData()
        binding.courseDetailsGradesRecyclerView.adapter = adapter

        binding.tvCourseDetailsGradesSeeMore.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_offlineCourseDetailsFragment_to_gradesFragment)
        }

        val sharedPref = requireActivity().getPreferences(MODE_PRIVATE)

        if (sharedPref!!.contains(courseUuid.toString())) {
            objective = sharedPref.getInt(courseUuid.toString(), -1)

        }
        val bottomSheetSetObjectiveBinding = BottomSheetSetObjectiveBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setCancelable(true)
        dialog.setContentView(bottomSheetSetObjectiveBinding.root)
        binding.tvCourseDetailsObjectiveEdit.setOnClickListener {
            dialog.show()
        }

        bottomSheetSetObjectiveBinding.bottomSheetObjectiveSetButton.setOnClickListener {
            try {
                objective = bottomSheetSetObjectiveBinding.etObjectiveBottomSheet.text.toString().toInt()
                sharedPref.edit {
                    putInt(courseUuid.toString(), objective)
                }
                binding.linearProgressIndicator.max = objective
                val decimalSymbols: DecimalFormatSymbols = DecimalFormatSymbols.getInstance()
                decimalSymbols.decimalSeparator = '.'
                val df = DecimalFormat("#.##", decimalSymbols)
                df.roundingMode = RoundingMode.HALF_UP
                val number = df.format(average.toFloat()).toFloat()
                binding.linearProgressIndicator.progress = number.roundToInt()
                binding.tvCourseDetailsObjectiveYourObjectiveGrade.text = objective.toString()
                if (objective > 0) binding.tvCourseDetailsObjectiveYourAverageGrade.text =
                    df.format(average.toFloat())
                dialog.dismiss()
            } catch (e: Exception) {
                Toast.makeText(context, getString(R.string.please_enter_a_valid_value), Toast.LENGTH_LONG).show()
            }
        }

        bottomSheetSetObjectiveBinding.bottomSheetCancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }


    private fun observeLiveData() {
        viewModel.offlineCourseLiveData.observe(viewLifecycleOwner) { course ->

            binding.apply {

                ivCourseDetailsIndicator.setBackgroundColor(course.color)
                ivCourseDetailsGraphBackground.setBackgroundColor(course.color)
                ivGradesBackground.setBackgroundColor(course.color)

                linearProgressIndicator.trackColor = adjustAlpha(course.color)
                linearProgressIndicator.setIndicatorColor(course.color)

                circularProgressIndicator.trackColor = adjustAlpha(course.color)
                circularProgressIndicator.setIndicatorColor(course.color)

                cvCourseSummary.visibility = View.VISIBLE
                cvCourseDetailsAverage.visibility = View.VISIBLE
                cvCourseDetailsObjective.visibility = View.VISIBLE
                cvCourseDetailsGrades.visibility = View.VISIBLE

                tvCourseDetailsTeacherName.text = course.teacherName
                tvCourseDetailsCourseRoom.text = course.location
                val combinedDate = "${course.day}, ${course.startTime}"
                tvCourseDetailsCourseDate.text = combinedDate
            }


        }

        val decimalSymbols: DecimalFormatSymbols = DecimalFormatSymbols.getInstance()
        decimalSymbols.decimalSeparator = '.'
        val df = DecimalFormat("#.##", decimalSymbols)
        df.roundingMode = RoundingMode.HALF_UP
        viewModel.gradesLiveData.observe(viewLifecycleOwner) { grades ->
            grades?.let {
                if (grades.isNotEmpty()) binding.tvCourseDetailsNoGrades.visibility = View.GONE
                adapter.submitList(grades)
                val verbalGrades = ArrayList<Grade>()
                val writtenGrades = ArrayList<Grade>()
                grades.forEach {
                    if (it.type == "Written") {
                        writtenGrades.add(it)
                    } else {
                        verbalGrades.add(it)
                    }
                }
                average = calculateAverage(grades)
                val writtenAverage = calculateAverage(writtenGrades)
                val verbalAverage = calculateAverage(verbalGrades)

                if (average.toFloat() > 0) {
                    binding.tvCourseDetailsAverageNumber.text = df.format(average.toFloat())
                    val number = df.format(average.toFloat()).toFloat()
                    binding.circularProgressIndicator.progress = number.roundToInt()
                    if (objective >= 0) {
                        binding.tvCourseDetailsObjectiveYourAverageGrade.text = df.format(average.toFloat())
                        binding.linearProgressIndicator.max = objective
                        binding.linearProgressIndicator.progress = number.roundToInt()
                    }
                }
                if (objective >= 0) binding.tvCourseDetailsObjectiveYourObjectiveGrade.text = objective.toString()

                if (writtenAverage.toFloat() > 0) binding.tvCourseDetailsWrittenGrade.text =
                    df.format(writtenAverage.toFloat())
                if (verbalAverage.toFloat() > 0) binding.tvCourseDetailsOralGrade.text =
                    df.format(verbalAverage.toFloat())
            }
        }
    }

    private fun calculateAverage(grades: List<Grade>): String {
        var totalWeight = 0
        var average = 0F
        return if (grades.isNotEmpty()) {
            grades.forEach { grade ->
                totalWeight += grade.weight
            }
            grades.forEach { grade ->
                average += ((grade.grade) * (grade.weight.toFloat() / totalWeight))
            }
            average.toString()
        } else "0"

    }

    private fun adjustAlpha(color: Int): Int {
        val factor = 0.1f
        val alpha = (Color.alpha(color) * factor).roundToInt()
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}