using Microsoft.AspNetCore.Mvc;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace WindowsShinshilla.Controllers
{
    public class Review
    {
        public string Name { get; set; } = "";
        public int Rating { get; set; }
        public string Comment { get; set; } = "";
        public string Date { get; set; } = "";
    }

    public class HomeController : Controller
    {
        private readonly string ReviewsPath = Path.Combine(Directory.GetCurrentDirectory(), "reviews.json");

        [HttpGet("/")]
        public IActionResult Index()
        {
            return View("Index");
        }

        [HttpGet("/wallpapers")]
        public IActionResult Wallpapers()
        {
            return View("Wallpapers");
        }

        [HttpGet("/otzivy")]
        public IActionResult Otzivy()
        {
            var reviews = LoadReviews();
            // reverse to show newest first (mimic Flask code)
            reviews.Reverse();
            return View("Otzivy", reviews);
        }

        [HttpPost("/otzivy")]
        public IActionResult PostOtzivy([FromForm] string name, [FromForm] int rating, [FromForm] string comment)
        {
            var reviews = LoadReviews();
            var review = new Review
            {
                Name = string.IsNullOrWhiteSpace(name) ? "Аноним" : name,
                Rating = rating,
                Comment = comment ?? "",
                Date = DateTime.Now.ToString("yyyy-MM-dd HH:mm")
            };
            reviews.Add(review);
            SaveReviews(reviews);
            return Redirect("/otzivy");
        }

        private List<Review> LoadReviews()
        {
            if (!System.IO.File.Exists(ReviewsPath))
                return new List<Review>();
            try
            {
                var txt = System.IO.File.ReadAllText(ReviewsPath);
                var list = JsonSerializer.Deserialize<List<Review>>(txt);
                return list ?? new List<Review>();
            }
            catch
            {
                return new List<Review>();
            }
        }

        private void SaveReviews(List<Review> reviews)
        {
            var opts = new JsonSerializerOptions { WriteIndented = true };
            System.IO.File.WriteAllText(ReviewsPath, JsonSerializer.Serialize(reviews, opts));
        }

        [HttpGet("/download")]
        public IActionResult Download()
        {
            return View("Download");
        }

        [HttpGet("/photos")]
        public IActionResult Photos()
        {
            return View("Photos");
        }

        [HttpGet("/history")]
        public IActionResult History()
        {
            return View("History");
        }

        [HttpGet("/instrs")]
        public IActionResult Instrs()
        {
            return View("Instrs");
        }
    }
}
